package com.egoriku.radiotok.exoplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.R
import com.egoriku.radiotok.exoplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class FavoriteActionProvider(
    private val context: Context,
    private val likedRadioStationsHolder: LikedRadioStationsHolder,
    private val radioStationsHolder: RadioStationsHolder
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction? {
        val radio = radioStationsHolder.allRadioStations.getOrNull(player.currentWindowIndex)

        if (radio != null) {
            return if (likedRadioStationsHolder.likedRadioStations.contains(radio)) {
                PlaybackStateCompat.CustomAction
                    .Builder(
                        ACTION_FAVORITE_REMOVE,
                        context.getString(R.string.custom_action_favorite_remove),
                        R.drawable.ic_favorite
                    ).build()
            } else {
                PlaybackStateCompat.CustomAction
                    .Builder(
                        ACTION_FAVORITE_ADD,
                        context.getString(R.string.custom_action_favorite_add),
                        R.drawable.ic_favorite_border
                    ).build()
            }
        }

        return null
    }

    override fun onCustomAction(
        player: Player,
        controlDispatcher: ControlDispatcher,
        action: String,
        extras: Bundle?
    ) {
        if (radioStationsHolder.allRadioStations.isNotEmpty()) {
            val radioItemModel = radioStationsHolder.allRadioStations[player.currentWindowIndex]
            when (action) {
                ACTION_FAVORITE_REMOVE -> likedRadioStationsHolder.unlike(
                    player = player,
                    radioItemModel = radioItemModel,
                    controlDispatcher = controlDispatcher
                )
                ACTION_FAVORITE_ADD -> likedRadioStationsHolder.like(
                    player = player,
                    radioItemModel = radioItemModel,
                    controlDispatcher = controlDispatcher
                )
            }
        }
    }

    companion object {
        const val ACTION_FAVORITE_ADD = "ACTION_FAVORITE_ADD"
        const val ACTION_FAVORITE_REMOVE = "ACTION_FAVORITE_REMOVE"
    }
}