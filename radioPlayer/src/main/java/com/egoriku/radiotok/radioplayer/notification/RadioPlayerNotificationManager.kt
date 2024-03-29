package com.egoriku.radiotok.radioplayer.notification

import android.content.Context
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_DISLIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_LIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_NEXT
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_UNLIKE
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.RadioStateMediator
import com.egoriku.radiotok.radioplayer.ext.id
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.R

class RadioPlayerNotificationManager(
    context: Context,
    channelId: String,
    notificationId: Int,
    mediaDescriptionAdapter: MediaDescriptionAdapter,
    notificationListener: NotificationListener,
    customActionReceiver: CustomActionReceiver,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val radioStateMediator: RadioStateMediator,
) : PlayerNotificationManager(
    context,
    channelId,
    notificationId,
    mediaDescriptionAdapter,
    notificationListener,
    customActionReceiver,
    R.drawable.exo_notification_small_icon,
    R.drawable.exo_notification_play,
    R.drawable.exo_notification_pause,
    R.drawable.exo_notification_stop,
    R.drawable.exo_notification_rewind,
    R.drawable.exo_notification_fastforward,
    R.drawable.exo_notification_previous,
    R.drawable.exo_notification_next,
    null
) {

    override fun getActionIndicesForCompactView(
        actionNames: MutableList<String>,
        player: Player,
    ): IntArray {
        val pauseActionIndex = actionNames.indexOf(ACTION_PAUSE)
        val playActionIndex = actionNames.indexOf(ACTION_PLAY)
        val dislikeActionIndex = actionNames.indexOf(CUSTOM_ACTION_DISLIKE)
        val skipNextActionIndex = actionNames.indexOf(CUSTOM_ACTION_NEXT)

        return buildList {
            if (dislikeActionIndex != -1) {
                add(dislikeActionIndex)
            }
            val playWhenReady = player.playWhenReady
            if (pauseActionIndex != -1 && playWhenReady) {
                add(pauseActionIndex)
            } else if (playActionIndex != -1 && !playWhenReady) {
                add(playActionIndex)
            }
            if (skipNextActionIndex != -1) {
                add(skipNextActionIndex)
            }
        }.toIntArray()
    }

    override fun getActions(player: Player): List<String> {
        return buildList {
            add(CUSTOM_ACTION_DISLIKE)

            when {
                isPlaying(player) -> add(ACTION_PAUSE)
                else -> add(ACTION_PLAY)
            }

            if (currentRadioQueueHolder.isRandomRadio()) {
                add(CUSTOM_ACTION_NEXT)
            } else {
                if (player.hasNextMediaItem()) {
                    add(CUSTOM_ACTION_NEXT)
                }
            }

            val currentRadioId = currentRadioQueueHolder.getMediaMetadataOrNull(
                position = player.currentMediaItemIndex
            )?.id ?: return@buildList

            if (radioStateMediator.isLiked(currentRadioId)) {
                add(CUSTOM_ACTION_UNLIKE)
            } else {
                add(CUSTOM_ACTION_LIKE)
            }
        }
    }

    private fun isPlaying(player: Player) = player.playbackState != Player.STATE_ENDED &&
            player.playbackState != Player.STATE_IDLE &&
            player.playWhenReady
}