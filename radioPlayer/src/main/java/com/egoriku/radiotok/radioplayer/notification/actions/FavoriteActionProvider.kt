package com.egoriku.radiotok.radioplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.os.bundleOf
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.constant.CustomAction.ACTION_TOGGLE_FAVORITE
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.radioplayer.ext.id
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class FavoriteActionProvider(
    private val context: Context,
    private val likedRadioStationsHolder: LikedRadioStationsHolder,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val onInvalidateNotification: () -> Unit
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction? {
        val mediaItem =
            currentRadioQueueHolder.currentMediaQueue.getOrNull(player.currentWindowIndex)

        return if (mediaItem == null) {
            null
        } else {
            if (likedRadioStationsHolder.likedRadioStationsIds.contains(mediaItem.id)) {
                PlaybackStateCompat.CustomAction
                    .Builder(
                        ACTION_TOGGLE_FAVORITE,
                        context.getString(R.string.custom_action_favorite_remove),
                        R.drawable.ic_favorite
                    ).setExtras(
                        bundleOf("IS_LIKED" to true)
                    ).build()
            } else {
                PlaybackStateCompat.CustomAction
                    .Builder(
                        ACTION_TOGGLE_FAVORITE,
                        context.getString(R.string.custom_action_favorite_add),
                        R.drawable.ic_favorite_border
                    )
                    .setExtras(
                        bundleOf("IS_LIKED" to false)
                    )
                    .build()
            }
        }
    }

    override fun onCustomAction(
        player: Player,
        controlDispatcher: ControlDispatcher,
        action: String,
        extras: Bundle?
    ) {
        if (currentRadioQueueHolder.currentMediaQueue.isNotEmpty()) {
            val id = currentRadioQueueHolder.currentMediaQueue[player.currentWindowIndex].id

            if (likedRadioStationsHolder.likedRadioStationsIds.contains(id)) {
                likedRadioStationsHolder.unlike(id = id)
            } else {
                likedRadioStationsHolder.like(id = id)
            }

            onInvalidateNotification()
        }
    }
}