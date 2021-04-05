package com.egoriku.radiotok.exoplayer.service

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.media.MediaBrowserServiceCompat
import com.egoriku.radiotok.R
import com.egoriku.radiotok.domain.IRadioFetchUseCase
import com.egoriku.radiotok.domain.model.RadioItemModel
import com.egoriku.radiotok.exoplayer.*
import com.egoriku.radiotok.exoplayer.PlayerConstants.MEDIA_ROOT_ID
import com.egoriku.radiotok.exoplayer.PlayerConstants.NETWORK_ERROR
import com.egoriku.radiotok.exoplayer.PlayerConstants.NOTIFICATION_CHANNEL_ID
import com.egoriku.radiotok.exoplayer.PlayerConstants.NOTIFICATION_ID
import com.egoriku.radiotok.exoplayer.PlayerConstants.SERVICE_TAG
import com.egoriku.radiotok.exoplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.egoriku.radiotok.exoplayer.listener.EventHandler
import com.egoriku.radiotok.exoplayer.listener.RadioPlaybackPreparer
import com.egoriku.radiotok.exoplayer.listener.RadioPlayerEventListener
import com.egoriku.radiotok.exoplayer.notification.NotificationCustomActionReceiver
import com.egoriku.radiotok.exoplayer.notification.RadioPlayerNotificationManager
import com.egoriku.radiotok.exoplayer.notification.actions.DislikeActionProvider
import com.egoriku.radiotok.exoplayer.notification.actions.FavoriteActionProvider
import com.egoriku.radiotok.exoplayer.notification.actions.NextActionProvider
import com.egoriku.radiotok.exoplayer.notification.adapter.DescriptionAdapter
import com.egoriku.radiotok.exoplayer.notification.listener.NotificationMediaButtonEventHandler
import com.egoriku.radiotok.exoplayer.notification.listener.RadioPlayerNotificationListener
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.NotificationUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class RadioService : MediaBrowserServiceCompat() {

    private val defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory by inject()
    private val likedRadioStationsHolder: LikedRadioStationsHolder by inject()
    private val radioStationsHolder: RadioStationsHolder by inject()
    private val radioFetchUseCase: IRadioFetchUseCase by inject()
    private val simpleExoPlayer: SimpleExoPlayer by inject()

    private val eventHandler = EventHandler()

    private var playerNotificationManager: PlayerNotificationManager by Delegates.notNull()

    private var radioPlayerEventListener: RadioPlayerEventListener by Delegates.notNull()

    private var mediaSession: MediaSessionCompat by Delegates.notNull()
    private var mediaSessionConnector: MediaSessionConnector by Delegates.notNull()
    private var mediaController: MediaControllerCompat by Delegates.notNull()

    private var isPlayerInitialized = false
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
            setPlaybackPreparer(RadioPlaybackPreparer(radioStationsHolder) {
                preparePlayer(radioModel = it)
            })
            setCustomActionProviders(
                FavoriteActionProvider(
                    context = this@RadioService,
                    likedRadioStationsHolder = likedRadioStationsHolder,
                    radioStationsHolder = radioStationsHolder
                ),
                DislikeActionProvider(
                    context = this@RadioService,
                    radioStationsHolder = radioStationsHolder,
                    onDislike = {
                        likedRadioStationsHolder.dislike(radioItemModel = it)
                        serviceScope.launch {
                            eventHandler.playNext()
                        }
                    }
                ),
                NextActionProvider(
                    context = this@RadioService,
                    onNext = {
                        serviceScope.launch {
                            eventHandler.playNext()
                        }
                    }
                )
            )
            setQueueNavigator(
                RadioQueueNavigator(
                    mediaSession = mediaSession,
                    radioStationsHolder = radioStationsHolder,
                    onNext = {
                        serviceScope.launch {
                            eventHandler.playNext()
                        }
                    }
                )
            )
        }

        radioPlayerEventListener = RadioPlayerEventListener {
            stopForeground(false)
        }

        simpleExoPlayer.addListener(radioPlayerEventListener)

        serviceScope.launch {
            eventHandler.event.collect {
                when (it) {
                    EventHandler.Event.PlayNext -> preparePlayer(
                        radioModel = radioStationsHolder.randomRadioModel()
                    )
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
                mediaController = mediaController
            ),
            notificationListener = RadioPlayerNotificationListener(this),
            customActionReceiver = NotificationCustomActionReceiver(
                context = this,
                radioStationsHolder = radioStationsHolder,
                onLike = {
                    likedRadioStationsHolder.like(
                        playerNotificationManager = playerNotificationManager,
                        radioItemModel = it
                    )
                },
                onUnlike = {
                    likedRadioStationsHolder.unlike(
                        playerNotificationManager = playerNotificationManager,
                        radioItemModel = it
                    )
                },
                onDislike = {
                    likedRadioStationsHolder.dislike(radioItemModel = it)
                    serviceScope.launch {
                        eventHandler.playNext()
                    }
                }
            ),
            radioStationsHolder = radioStationsHolder,
            likedRadioStationsHolder = likedRadioStationsHolder
        )
        playerNotificationManager.setSmallIcon(R.drawable.ic_radio)
        playerNotificationManager.setMediaSessionToken(mediaSession.sessionToken)
        playerNotificationManager.setPlayer(simpleExoPlayer)
    }

    private fun preparePlayer(radioModel: RadioItemModel) {
        simpleExoPlayer.setMediaSource(
            radioStationsHolder.asMediaSource(
                dataSourceFactory = defaultHttpDataSourceFactory,
                radioModel = radioModel
            )
        )
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
    ) = BrowserRoot(MEDIA_ROOT_ID, null)

    override fun onLoadChildren(
        parentId: String,
        result: Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        Log.d("kek", "onLoadChildren")

        if (parentId != MEDIA_ROOT_ID) {
            return
        }

        serviceScope.launch {
            val load = radioFetchUseCase.load()

            if (load.isEmpty()) {
                Log.d("kek", "onLoadChildren empty")

                mediaSession.sendSessionEvent(NETWORK_ERROR, null)
                result.detach()
            } else {
                Log.d("kek", "onLoadChildren not empty")

                result.sendResult(radioStationsHolder.asMediaItems())

                if (!isPlayerInitialized && radioStationsHolder.allRadioStations.isNotEmpty()) {

                    eventHandler.playNext()

                    isPlayerInitialized = true
                }
            }
        }

        result.detach()
    }
}
