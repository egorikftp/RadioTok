package com.egoriku.radiotok.radioplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.constant.CustomAction.ACTION_DISLIKE
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.ext.id
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class DislikeActionProvider(
    private val context: Context,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val onDislike: (String) -> Unit
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction {
        return PlaybackStateCompat.CustomAction.Builder(
            ACTION_DISLIKE,
            context.getString(R.string.custom_action_dislike),
            R.drawable.ic_not_interested
        ).build()
    }

    override fun onCustomAction(player: Player, action: String, extras: Bundle?) {
        val currentMediaMetadata = currentRadioQueueHolder.getMediaMetadataOrNull(
            position = player.currentMediaItemIndex
        ) ?: return

        onDislike(currentMediaMetadata.id)
    }
}