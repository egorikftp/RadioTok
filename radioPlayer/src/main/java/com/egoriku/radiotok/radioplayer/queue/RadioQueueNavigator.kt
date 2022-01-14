package com.egoriku.radiotok.radioplayer.queue

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
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
        return when (val metadata = currentRadioQueueHolder.getMediaMetadataOrNull(windowIndex)) {
            null -> getEmptyMediaDescription()
            else -> metadata.description
        }
    }

    override fun getSupportedQueueNavigatorActions(player: Player): Long {
        var actions = 0L

        if (currentRadioQueueHolder.isRandomRadio()) {
            logD("getSupportedQueueNavigatorActions: Random")
            actions = actions or
                    PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
        } else if (currentRadioQueueHolder.isSingle()) {
            logD("getSupportedQueueNavigatorActions: Single")
        } else {
            logD("getSupportedQueueNavigatorActions: Else")

            actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM

            if (player.hasNextMediaItem()) {
                actions = actions or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            }
        }

        return actions
    }

    override fun onSkipToNext(player: Player) {
        when {
            currentRadioQueueHolder.isRandomRadio() -> onNextRandom()
            else -> {
                if (player.hasNextMediaItem()) {
                    player.seekToNextMediaItem()
                } else {
                    player.stop()
                }
            }
        }
    }

    private fun getEmptyMediaDescription() = MediaDescriptionCompat.Builder().build()
}