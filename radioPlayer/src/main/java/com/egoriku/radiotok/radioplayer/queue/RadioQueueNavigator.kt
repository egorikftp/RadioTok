package com.egoriku.radiotok.radioplayer.queue

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.TimelineQueueNavigator

class RadioQueueNavigator(
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val onNextRandom: () -> Unit,
    mediaSession: MediaSessionCompat
) : TimelineQueueNavigator(mediaSession) {

    override fun getMediaDescription(
        player: Player,
        windowIndex: Int
    ): MediaDescriptionCompat {
        val currentMediaMetadata = currentRadioQueueHolder.getMediaMetadataOrNull(windowIndex)

        return requireNotNull(currentMediaMetadata).description
    }

    override fun getSupportedQueueNavigatorActions(player: Player): Long {
        var actions = 0L

        val isSingle = currentRadioQueueHolder.currentMediaPath != MediaPath.Single

        if (isSingle) {
            actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM
            actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
        }

        return actions
    }

    override fun onSkipToNext(
        player: Player,
        controlDispatcher: ControlDispatcher
    ) {
        when {
            currentRadioQueueHolder.isRandomRadio() -> onNextRandom()
            else -> {
                if (player.hasNextWindow()) {
                    player.seekToNextWindow()
                } else {
                    player.stop()
                }
            }
        }
    }
}