package com.egoriku.radiotok.radioplayer.listener

import com.egoriku.radiotok.common.ext.logD
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player

class RadioPlayerEventListener(
    private val onStopForeground: () -> Unit
) : Player.Listener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            onStopForeground()
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        logD("PlayerEventListener: An unknown error occurred: $error")
    }
}