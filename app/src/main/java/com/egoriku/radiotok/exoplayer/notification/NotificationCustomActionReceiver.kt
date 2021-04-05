package com.egoriku.radiotok.exoplayer.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.egoriku.radiotok.R
import com.egoriku.radiotok.domain.model.RadioItemModel
import com.egoriku.radiotok.exoplayer.data.RadioStationsHolder
import com.egoriku.radiotok.exoplayer.notification.CustomAction.CUSTOM_ACTION_DISLIKE
import com.egoriku.radiotok.exoplayer.notification.CustomAction.CUSTOM_ACTION_LIKE
import com.egoriku.radiotok.exoplayer.notification.CustomAction.CUSTOM_ACTION_NEXT
import com.egoriku.radiotok.exoplayer.notification.CustomAction.CUSTOM_ACTION_UNLIKE
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlin.random.Random

class NotificationCustomActionReceiver(
    private val context: Context,
    private val radioStationsHolder: RadioStationsHolder,
    private val onLike: (RadioItemModel) -> Unit,
    private val onUnlike: (RadioItemModel) -> Unit,
    private val onDislike: (RadioItemModel) -> Unit
) : PlayerNotificationManager.CustomActionReceiver {

    private val skipToNextAction = NotificationCompat.Action(
        R.drawable.ic_skip_next,
        context.getString(R.string.playback_action_skip_to_next),
        MediaButtonReceiver.buildMediaButtonPendingIntent(
            context,
            PlaybackStateCompat.ACTION_SKIP_TO_NEXT
        )
    )

    private val likeAction = NotificationCompat.Action(
        R.drawable.ic_favorite_border,
        context.getString(R.string.playback_action_like),
        buildPendingIntent(CUSTOM_ACTION_LIKE)
    )

    private val unlikeAction = NotificationCompat.Action(
        R.drawable.ic_favorite,
        context.getString(R.string.playback_action_unlike),
        buildPendingIntent(CUSTOM_ACTION_UNLIKE)
    )

    private val dislikeAction = NotificationCompat.Action(
        R.drawable.ic_not_interested,
        context.getString(R.string.playback_action_dislike),
        buildPendingIntent(CUSTOM_ACTION_DISLIKE)
    )

    override fun getCustomActions(player: Player) = listOf(
        CUSTOM_ACTION_NEXT,
        CUSTOM_ACTION_LIKE,
        CUSTOM_ACTION_UNLIKE,
        CUSTOM_ACTION_DISLIKE
    )

    override fun createCustomActions(context: Context, instanceId: Int) = mapOf(
        CUSTOM_ACTION_NEXT to skipToNextAction,
        CUSTOM_ACTION_LIKE to likeAction,
        CUSTOM_ACTION_UNLIKE to unlikeAction,
        CUSTOM_ACTION_DISLIKE to dislikeAction
    )

    override fun onCustomAction(player: Player, action: String, intent: Intent) {
        val radioItemModel = radioStationsHolder.currentRadioStation

        when (action) {
            CUSTOM_ACTION_LIKE -> onLike(radioItemModel)
            CUSTOM_ACTION_UNLIKE -> onUnlike(radioItemModel)
            CUSTOM_ACTION_DISLIKE -> onDislike(radioItemModel)
        }
    }

    private fun buildPendingIntent(
        action: String,
        instanceId: Int = Random.nextInt()
    ) = PendingIntent.getBroadcast(
        context,
        instanceId,
        Intent(action).setPackage(context.packageName),
        PendingIntent.FLAG_CANCEL_CURRENT
    )
}

