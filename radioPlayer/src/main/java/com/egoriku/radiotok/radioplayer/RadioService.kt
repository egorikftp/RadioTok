package com.egoriku.radiotok.radioplayer

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_ROOT
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NETWORK_ERROR
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NOTIFICATION_CHANNEL_ID
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.NOTIFICATION_ID
import com.egoriku.radiotok.radioplayer.constant.PlayerConstants.SERVICE_TAG
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.LikedRadioStationsHolder
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
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.util.NotificationUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class RadioService : MediaBrowserServiceCompat() {

    private val likedRadioStationsHolder: LikedRadioStationsHolder by inject()
    private val currentRadioQueueHolder: CurrentRadioQueueHolder by inject()
    private val radioCacheMediator: IRadioCacheMediator by inject()
    private val simpleExoPlayer: SimpleExoPlayer by inject()

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
            PendingIntent.getActivity(this, 0, it, 0)
        }

        mediaSession = MediaSessionCompat(this, SERVICE_TAG).apply {
            setSessionActivity(activityIntent)
            isActive = true
        }

        sessionToken = mediaSession.sessionToken

        mediaController = MediaControllerCompat(applicationContext, mediaSession.sessionToken)

        initializePlayerNotificationManager()

        mediaSessionConnector = MediaSessionConnector(mediaSession).apply {
            setPlayer(simpleExoPlayer)

            setMediaButtonEventHandler(
                NotificationMediaButtonEventHandler(
                    onNext = {
                        serviceScope.launch {
                            eventHandler.playNext()
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
                    likedRadioStationsHolder = likedRadioStationsHolder,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onInvalidateNotification = {
                        playerNotificationManager.invalidate()
                    }
                ),
                DislikeActionProvider(
                    context = this@RadioService,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onDislike = {
                        likedRadioStationsHolder.dislike(id = it)
                        serviceScope.launch {
                            eventHandler.playNext()
                        }
                    }
                )
            )
            setQueueNavigator(
                RadioQueueNavigator(
                    mediaSession = mediaSession,
                    currentRadioQueueHolder = currentRadioQueueHolder,
                    onNext = {
                        serviceScope.launch {
                            eventHandler.playNext()
                        }
                    }
                )
            )
        }

        radioPlayerEventListener = RadioPlayerEventListener(
            onStopForeground = {
                stopForeground(false)
            }
        )

        simpleExoPlayer.addListener(radioPlayerEventListener)

        serviceScope.launch {
            eventHandler.event.collect {
                when (it) {
                    EventHandler.Event.PlayNext -> {
                        radioCacheMediator.loadNextRadio()
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
                    likedRadioStationsHolder.like(
                        playerNotificationManager = playerNotificationManager,
                        id = it
                    )

                    mediaSessionConnector.invalidateMediaSessionPlaybackState()
                },
                onUnlike = {
                    likedRadioStationsHolder.unlike(
                        playerNotificationManager = playerNotificationManager,
                        id = it
                    )
                    mediaSessionConnector.invalidateMediaSessionPlaybackState()

                },
                onDislike = {
                    likedRadioStationsHolder.dislike(id = it)
                    serviceScope.launch {
                        eventHandler.playNext()
                    }
                }
            ),
            currentRadioQueueHolder = currentRadioQueueHolder,
            likedRadioStationsHolder = likedRadioStationsHolder
        )
        playerNotificationManager.setSmallIcon(R.drawable.ic_radio)
        playerNotificationManager.setMediaSessionToken(mediaSession.sessionToken)
        playerNotificationManager.setPlayer(simpleExoPlayer)
    }

    private fun preparePlayer() {
        simpleExoPlayer.setMediaSource(currentRadioQueueHolder.currentMediaSource)
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.prepare()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession.run {
            isActive = false
            release()
        }

        simpleExoPlayer.removeListener(radioPlayerEventListener)
        simpleExoPlayer.release()

        playerNotificationManager.setPlayer(null)

        serviceScope.cancel()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ) = BrowserRoot(MEDIA_PATH_ROOT, null)

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        logD("onLoadChildren: $parentId")

        result.detach()

        serviceScope.launch {
            val mediaPath = MediaPath.fromParentIdOrThrow(parentId)
            val mediaBrowseItems = radioCacheMediator.getMediaBrowserItemsBy(
                mediaPath = mediaPath
            )

            when (mediaPath) {
                is MediaPath.Root -> result.sendResult(mediaBrowseItems)
                else -> {
                    if (mediaBrowseItems.isEmpty()) {
                        logD("onLoadChildren empty")

                        mediaSession.sendSessionEvent(NETWORK_ERROR, null)
                        result.detach()
                    } else {
                        logD("onLoadChildren not empty")

                        result.sendResult(mediaBrowseItems)
                        preparePlayer()
                    }
                }
            }
        }
    }
}
