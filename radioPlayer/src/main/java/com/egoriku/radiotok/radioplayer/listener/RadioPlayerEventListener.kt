package com.egoriku.radiotok.radioplayer.listener

import com.egoriku.radiotok.common.ext.logD
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

class RadioPlayerEventListener(
    private val onStopForeground: () -> Unit,
    private val onError: () -> Unit
) : Player.Listener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            onStopForeground()
        }
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)

        onError()

        if (error is ExoPlaybackException) {
            when (error.type) {
                ExoPlaybackException.TYPE_SOURCE -> logD("onPlayerError, TYPE_SOURCE: " + error.sourceException.toString())
                ExoPlaybackException.TYPE_RENDERER -> logD("onPlayerError, TYPE_RENDERER: " + error.rendererException.toString())
                ExoPlaybackException.TYPE_UNEXPECTED -> logD("onPlayerError, TYPE_UNEXPECTED: " + error.unexpectedException.toString())
                else -> logD("onPlayerError, ETC: " + error.sourceException.toString())
            }
        }
    }
}