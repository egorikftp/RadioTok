package com.egoriku.radiotok.radioplayer

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.ERROR_CODE_APP_ERROR
import android.util.Pair
import androidx.core.os.bundleOf
import androidx.media.MediaBrowserServiceCompat
import androidx.media.utils.MediaConstants.*
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NOTIFICATION_CHANNEL_ID
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NOTIFICATION_ID
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.SERVICE_TAG
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.RadioStateMediator
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.listener.EventHandler
import com.egoriku.radiotok.radioplayer.listener.RadioPlaybackPreparer
import com.egoriku.radiotok.radioplayer.listener.RadioPlayerEventListener
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.notification.NotificationCustomActionReceiver
import com.egoriku.radiotok.radioplayer.notification.RadioPlayerNotificationManager
import com.egoriku.radiotok.radioplayer.notification.actions.DislikeActionProvider
import com.egoriku.radiotok.radioplayer.notification.actions.FavoriteActionProvider
import com.egoriku.radiotok.radioplayer.notification.description.DescriptionAdapter
import com.egoriku.radiotok.radioplayer.notification.listener.NotificationMediaButtonEventHandler
import com.egoriku.radiotok.radioplayer.notification.listener.RadioPlayerNotificationListener
import com.egoriku.radiotok.radioplayer.queue.RadioQueueNavigator
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

private const val MEDIA_SEARCH_SUPPORTED = "android.media.browse.SEARCH_SUPPORTED"

class RadioService : MediaBrowserServiceCompat() {

    private val radioStateMediator: RadioStateMediator by inject()
    private val currentRadioQueueHolder: CurrentRadioQueueHolder by inject()
    private val radioCacheMediator: IRadioCacheMediator by inject()
    private val exoPlayer: ExoPlayer by inject()

    private val eventHandler = EventHandler()

    private var playerNotificationManager: PlayerNotificationManager by Delegates.notNull()

    private var radioPlayerEventListener: RadioPlayerEventListener by Delegates.notNull()

    private var mediaSession: MediaSessionCompat by Delegates.notNull()
    private var mediaSessionConnector: MediaSessionConnector by Delegates.notNull()
    private var mediaController: MediaControllerCompat by Delegates.notNull()

    var isForegroundService: Boolean = false

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onCreate() {
        super.onCreate()

        val activityIntent = packageManager?.getLaunchIntentForPackage(packageName)?.let {
            PendingIntent.getActivity(this, 0, it, FLAG_IMMUTABLE)
        }

        mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }

        sessionToken = mediaSession.sessionToken

        mediaController = MediaControllerCompat(applicationContext, mediaSession.sessionToken)

        initializePlayerNotificationManager()

        mediaSessionConnector = MediaSessionConnector(mediaSession).apply {
            setPlayer(exoPlayer)

            setErrorMessageProvider { error: PlaybackException ->
                if (error is ExoPlaybackException) {
                    val message = when (error.type) {
                        ExoPlaybackException.TYPE_SOURCE -> {
                            logD("TYPE_SOURCE: " + error.sourceException.toString())
                            applicationContext.getString(R.string.playback_error_source)
                        }
                        ExoPlaybackException.TYPE_RENDERER -> "TYPE_RENDERER: " + error.rendererException.toString()
                        ExoPlaybackException.TYPE_UNEXPECTED -> "TYPE_UNEXPECTED: " + error.unexpectedException.toString()
                        else -> "ETC: " + error.sourceException.toString()
                    }

                    Pair(0, message)
                } else {
                    Pair(0, "unknown error")
                }
            }

            setMediaButtonEventHandler(
                NotificationMediaButtonEventHandler(
                    onNext = {
                        serviceScope.launch {
                            eventHandler.playNextRandom()
                        }
                    }
                )
            )
            setPlaybackPreparer(
                RadioPlaybackPreparer(radioCacheMediator = radioCacheMediator) {
                    preparePlayer()
                }
            )
            setCustomActionProviders(
                FavoriteActionProvider(
                    context = this@RadioService,
                    radioStateMediator = radioStateMediator,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onInvalidateNotification = {
                        playerNotificationManager.invalidate()
                    }
                ),
                DislikeActionProvider(
                    context = this@RadioService,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onDislike = {
                        radioStateMediator.exclude(id = it)
                        serviceScope.launch {
                            eventHandler.playNextRandom()
                        }
                    }
                )
            )
            setQueueNavigator(
                RadioQueueNavigator(
                    mediaSession = mediaSession,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onNextRandom = {
                        serviceScope.launch {
                            eventHandler.playNextRandom()
                        }
                    }
                )
            )
        }

        radioPlayerEventListener = RadioPlayerEventListener(
            onStopForeground = {
                stopForeground(false)
            },
            onError = {
                mediaSession.setPlaybackState(
                    PlaybackStateCompat.Builder()
                        .setState(PlaybackStateCompat.STATE_ERROR, 0L, 0.0f)
                        .setErrorMessage(ERROR_CODE_APP_ERROR, "Source error")
                        .build()
                )
            }
        )

        exoPlayer.addListener(radioPlayerEventListener)

        serviceScope.launch {
            eventHandler.event.collect {
                when (it) {
                    EventHandler.Event.PlayNextRandom -> {
                        radioCacheMediator.playNextRandom()
                        preparePlayer()
                    }
                }
            }
        }
    }

    private fun initializePlayerNotificationManager() {
        NotificationUtil.createNotificationChannel(
            this,
            NOTIFICATION_CHANNEL_ID,
            R.string.notification_channel_name,
            R.string.notification_channel_description,
            NotificationUtil.IMPORTANCE_LOW
        )

        playerNotificationManager = RadioPlayerNotificationManager(
            context = this,
            channelId = NOTIFICATION_CHANNEL_ID,
            notificationId = NOTIFICATION_ID,
            mediaDescriptionAdapter = DescriptionAdapter(
                context = this,
                mediaController = mediaController,
                currentRadioQueueHolder = currentRadioQueueHolder
            ),
            notificationListener = RadioPlayerNotificationListener(this),
            customActionReceiver = NotificationCustomActionReceiver(
                context = this,
                currentRadioQueueHolder = currentRadioQueueHolder,
                onLike = {
                    radioStateMediator.toggleLiked(
                        playerNotificationManager = playerNotificationManager,
                        id = it
                    )

                    mediaSessionConnector.invalidateMediaSessionPlaybackState()
                },
                onUnlike = {
                    radioStateMediator.toggleLiked(
                        playerNotificationManager = playerNotificationManager,
                        id = it
                    )
                    mediaSessionConnector.invalidateMediaSessionPlaybackState()

                },
                onDislike = {
                    radioStateMediator.exclude(id = it)
                    serviceScope.launch {
                        eventHandler.playNextRandom()
                    }
                }
            ),
            currentRadioQueueHolder = currentRadioQueueHolder,
            radioStateMediator = radioStateMediator
        )
        playerNotificationManager.setSmallIcon(R.drawable.ic_radio)
        playerNotificationManager.setMediaSessionToken(mediaSession.sessionToken)
        playerNotificationManager.setPlayer(exoPlayer)
    }

    private fun preparePlayer() {
        logD("preparePlayer, queue size: ${currentRadioQueueHolder.currentMediaSource.size}")
        exoPlayer.setMediaSource(currentRadioQueueHolder.currentMediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
    }

    override fun onDestroy() {
        super.onDestroy()
        logD("onDestroy service")
        mediaSession.run {
            isActive = false
            release()
        }

        exoPlayer.removeListener(radioPlayerEventListener)
        exoPlayer.release()

        playerNotificationManager.setPlayer(null)

        serviceScope.cancel()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?,
    ): BrowserRoot {
        val rootExtras = bundleOf(
            MEDIA_SEARCH_SUPPORTED to true,
            DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE to DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_GRID_ITEM,
            DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE to DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM
        )
        return BrowserRoot(PATH_ROOT, rootExtras)
    }

    override fun onSearch(
        query: String,
        extras: Bundle?,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>,
    ) {
        logD("onSearch: $query")
        super.onSearch(query, extras, result)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>,
    ) {
        logD("onLoadChildren: $parentId")

        result.detach()

        serviceScope.launch {
            logD("onLoadChildren: start load")

            val mediaPath = MediaPath.fromParentIdOrThrow(parentId)
            val mediaBrowseItems = radioCacheMediator.getMediaBrowserItemsBy(
                mediaPath = mediaPath
            )

            logD("onLoadChildren: mediaPath = $mediaPath, mediaBrowseItems = $mediaBrowseItems")

            if (mediaPath.isPlayable) {
                logD("onLoadChildren not empty")

                result.sendResult(mediaBrowseItems)
                preparePlayer()
            } else {
                if (mediaBrowseItems.isEmpty()) {
                    logD("onLoadChildren empty")

                    mediaSession.sendSessionEvent(PlayerConstants.NETWORK_ERROR, null)
                    result.detach()
                } else {
                    result.sendResult(mediaBrowseItems)
                }
            }
        }
    }
}
