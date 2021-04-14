package com.egoriku.radiotok.exoplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.R
import com.egoriku.radiotok.domain.model.RadioItemModel
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.egoriku.radiotok.exoplayer.notification.CustomAction.ACTION_DISLIKE
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class DislikeActionProvider(
    private val context: Context,
    private val radioStationsHolder: RadioStationsHolder,
    private val onDislike: (RadioItemModel) -> Unit
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction {
        return PlaybackStateCompat.CustomAction.Builder(
            ACTION_DISLIKE,
            context.getString(R.string.custom_action_dislike),
            R.drawable.ic_not_interested
        ).build()
    }

    override fun onCustomAction(
        player: Player,
        controlDispatcher: ControlDispatcher,
        action: String,
        extras: Bundle?
    ) = onDislike(radioStationsHolder.currentRadioStation)
}