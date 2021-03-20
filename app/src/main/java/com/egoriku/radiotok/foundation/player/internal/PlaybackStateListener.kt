package com.egoriku.radiotok.foundation.player.internal

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.HttpDataSource

class PlaybackStateListener : Player.EventListener {
    override fun onPlaybackStateChanged(playbackState: Int) {
        val stateString = when (playbackState) {
            ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE"
            ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING"
            ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY"
            ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED"
            else -> "UNKNOWN_STATE"
        }
        Log.d("kek", "changed state to $stateString")
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        if (error.type == ExoPlaybackException.TYPE_SOURCE) {
            val cause = error.sourceException
            if (cause is HttpDataSource.HttpDataSourceException) {
                Log.d("kek", "cause = $cause")
            }
        }
    }
}