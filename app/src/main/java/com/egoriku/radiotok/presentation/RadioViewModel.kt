package com.egoriku.radiotok.presentation

import androidx.lifecycle.ViewModel

class RadioViewModel(
    private val serviceConnection: IMusicServiceConnection
) : ViewModel(), IMusicServiceConnection by serviceConnection {

    fun next() {
        serviceConnection.transportControls.skipToNext()
    }

    fun togglePlayPause() {
        val playbackState = playbackState.value

        if (playbackState.isPrepared) {
            when {
                playbackState.isPlaying -> serviceConnection.transportControls.pause()
                playbackState.isPlayEnabled -> serviceConnection.transportControls.play()
            }
        } else {
            //serviceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }
}