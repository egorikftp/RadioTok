package com.egoriku.radiotok.exoplayer.listener

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player

class RadioPlayerEventListener(
    private val onStopForeground: () -> Unit
) : Player.EventListener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_READY && !playWhenReady) {
            onStopForeground()
        }
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        Log.d("PlayerEventListener", "An unknown error occured")
    }
}