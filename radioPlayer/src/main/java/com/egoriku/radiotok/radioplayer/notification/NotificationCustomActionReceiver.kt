package com.egoriku.radiotok.radioplayer.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.egoriku.radiotok.radioplayer.R
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_DISLIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_LIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_NEXT
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_UNLIKE
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.ext.id
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlin.random.Random

class NotificationCustomActionReceiver(
    private val context: Context,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val onLike: (String) -> Unit,
    private val onUnlike: (String) -> Unit,
    private val onDislike: (String) -> Unit
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
        val currentRadioStationMetadata = currentRadioQueueHolder.getMediaMetadataOrNull(
            position = player.currentWindowIndex
        ) ?: return

        when (action) {
            CUSTOM_ACTION_LIKE -> onLike(currentRadioStationMetadata.id)
            CUSTOM_ACTION_UNLIKE -> onUnlike(currentRadioStationMetadata.id)
            CUSTOM_ACTION_DISLIKE -> onDislike(currentRadioStationMetadata.id)
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

