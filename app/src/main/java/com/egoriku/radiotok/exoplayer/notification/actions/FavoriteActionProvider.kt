package com.egoriku.radiotok.exoplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.os.bundleOf
import com.egoriku.radiotok.R
import com.egoriku.radiotok.exoplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.egoriku.radiotok.exoplayer.notification.CustomAction.ACTION_TOGGLE_FAVORITE
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class FavoriteActionProvider(
    private val context: Context,
    private val likedRadioStationsHolder: LikedRadioStationsHolder,
    private val radioStationsHolder: RadioStationsHolder,
    private val onInvalidateNotification: () -> Unit
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction? {
        val radio = radioStationsHolder.currentRadioStation

        return if (likedRadioStationsHolder.likedRadioStations.contains(radio)) {
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

    override fun onCustomAction(
        player: Player,
        controlDispatcher: ControlDispatcher,
        action: String,
        extras: Bundle?
    ) {
        if (radioStationsHolder.allRadioStations.isNotEmpty()) {
            val radioItemModel = radioStationsHolder.currentRadioStation

            if (likedRadioStationsHolder.likedRadioStations.contains(radioItemModel)) {
                likedRadioStationsHolder.unlike(radioItemModel = radioItemModel)
            } else {
                likedRadioStationsHolder.like(radioItemModel = radioItemModel)
            }

            onInvalidateNotification()
        }
    }
}