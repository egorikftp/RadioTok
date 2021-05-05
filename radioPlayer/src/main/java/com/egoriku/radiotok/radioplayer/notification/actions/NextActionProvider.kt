package com.egoriku.radiotok.radioplayer.notification.actions

import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.constant.CustomAction.ACTION_SKIP_TO_NEXT
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector

class NextActionProvider(
    private val context: Context,
    private val onNext: () -> Unit
) : MediaSessionConnector.CustomActionProvider {

    override fun getCustomAction(player: Player): PlaybackStateCompat.CustomAction {
        return PlaybackStateCompat.CustomAction
            .Builder(
                ACTION_SKIP_TO_NEXT,
                context.getString(R.string.custom_action_skip_to_next),
                R.drawable.ic_skip_next
            ).build()
    }

    override fun onCustomAction(
        player: Player,
        controlDispatcher: ControlDispatcher,
        action: String,
        extras: Bundle?
    ) {
        onNext()
    }
}