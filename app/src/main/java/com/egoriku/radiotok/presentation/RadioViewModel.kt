package com.egoriku.radiotok.presentation

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import androidx.lifecycle.ViewModel
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_SHUFFLE_RANDOM
import com.egoriku.radiotok.radioplayer.ext.sendDislikeAction
import com.egoriku.radiotok.radioplayer.ext.sendLikeAction

class RadioViewModel(
    private val serviceConnection: IMusicServiceConnection
) : ViewModel(), IMusicServiceConnection by serviceConnection {

    private val _transportControls: MediaControllerCompat.TransportControls
        get() = serviceConnection.transportControls

    private val subscriptionCallback = object : MediaBrowserCompat.SubscriptionCallback() {
        override fun onChildrenLoaded(
            parentId: String,
            children: List<MediaBrowserCompat.MediaItem>
        ) {
            logD("parentId=$parentId, children size = ${children.size}")
            val itemsList = children.map { child ->
                val subtitle = child.description.subtitle ?: ""
                /* MediaItemData(
                     child.mediaId!!,
                     child.description.title.toString(),
                     subtitle.toString(),
                     child.description.iconUri!!,
                     child.isBrowsable,
                     getResourceForMediaId(child.mediaId!!)
                 )*/
            }
        }
    }

    init {
        serviceConnection.subscribe(SUB_PATH_SHUFFLE_RANDOM, subscriptionCallback)
    }

    fun nextRadioStation() = _transportControls.skipToNext()

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