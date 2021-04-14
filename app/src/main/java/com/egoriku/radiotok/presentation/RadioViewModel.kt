package com.egoriku.radiotok.presentation

import android.support.v4.media.session.MediaControllerCompat
import androidx.lifecycle.ViewModel
import com.egoriku.radiotok.exoplayer.ext.sendDislikeAction
import com.egoriku.radiotok.exoplayer.ext.sendLikeAction
import com.egoriku.radiotok.exoplayer.ext.sendSkipToNextAction

class RadioViewModel(
    private val serviceConnection: IMusicServiceConnection
) : ViewModel(), IMusicServiceConnection by serviceConnection {

    private val _transportControls: MediaControllerCompat.TransportControls
        get() = serviceConnection.transportControls

    fun nextRadioStation() = _transportControls.sendSkipToNextAction()

    fun dislikeRadioStation() = _transportControls.sendDislikeAction()

    fun likeRadioStation() = _transportControls.sendLikeAction()

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