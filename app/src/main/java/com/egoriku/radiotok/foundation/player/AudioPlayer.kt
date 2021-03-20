package com.egoriku.radiotok.foundation.player

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import com.egoriku.radiotok.extensions.consume
import com.egoriku.radiotok.foundation.player.internal.NotificationDescriptionAdapter
import com.egoriku.radiotok.foundation.player.internal.PlaybackStateListener
import com.egoriku.radiotok.foundation.player.internal.initNotificationChannel
import com.egoriku.radiotok.model.PlayerModel
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.EventLogger

const val CHANNEL_ID = "1"
private const val NOTIFICATION_ID = 1

@Composable
fun AudioPlayer(playerModel: PlayerModel, onNext: () -> Unit) {
    if (playerModel.streamUrl.isEmpty()) {
        return
    }

    val context = LocalContext.current

    var window by rememberSaveable { mutableStateOf(0) }
    var position by rememberSaveable { mutableStateOf(0L) }

    val defaultHttpDataSourceFactory = remember {
        DefaultHttpDataSource.Factory()
    }

    val playerListener = remember { PlaybackStateListener() }
    val defaultTrackSelector = remember { DefaultTrackSelector(context) }
    val eventLogger = remember { EventLogger(defaultTrackSelector) }

    val notificationDescriptionAdapter = remember {
        NotificationDescriptionAdapter(context)
    }

    notificationDescriptionAdapter.playerModel = playerModel

    val notificationManager = remember {
        PlayerNotificationManager(
            context,
            CHANNEL_ID,
            NOTIFICATION_ID,
            notificationDescriptionAdapter,
        ).apply {
            setUsePreviousAction(false)
            setControlDispatcher(object : DefaultControlDispatcher(0, 0) {
                override fun dispatchNext(player: Player) = consume { onNext() }
            })
        }
    }

    val player = remember {
        SimpleExoPlayer.Builder(context)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                false
            )
            .setTrackSelector(defaultTrackSelector)
            .build()
    }

    val mediaSource = if (playerModel.isHsl) {
        HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(playerModel.streamUrl))
    } else {
        ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(playerModel.streamUrl))
    }

    notificationManager.setPlayer(player)

    with(player) {
        setMediaSource(mediaSource)
        addAnalyticsListener(eventLogger)
        playWhenReady = true
        seekTo(window, position)
        addListener(playerListener)
        prepare()
    }

    fun updateState() {
        window = player.currentWindowIndex
        position = 0L.coerceAtLeast(player.contentPosition)
    }

    LaunchedEffect(Unit) {
        initNotificationChannel(context)
    }

    DisposableEffect(Unit) {
        onDispose {
            updateState()

            notificationManager.setPlayer(null)
            player.release()
            player.removeListener(playerListener)
            player.removeAnalyticsListener(eventLogger)
        }
    }
}