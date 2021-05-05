package com.egoriku.radiotok.radioplayer.notification

import android.content.Context
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_DISLIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_LIKE
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_NEXT
import com.egoriku.radiotok.radioplayer.constant.CustomAction.CUSTOM_ACTION_UNLIKE
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.LikedRadioStationsHolder
import com.egoriku.radiotok.radioplayer.ext.id
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class RadioPlayerNotificationManager(
    context: Context,
    channelId: String,
    notificationId: Int,
    mediaDescriptionAdapter: MediaDescriptionAdapter,
    notificationListener: NotificationListener,
    customActionReceiver: CustomActionReceiver,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder,
    private val likedRadioStationsHolder: LikedRadioStationsHolder,
) : PlayerNotificationManager(
    context,
    channelId,
    notificationId,
    mediaDescriptionAdapter,
    notificationListener,
    customActionReceiver
) {

    @OptIn(ExperimentalStdlibApi::class)
    override fun getActionIndicesForCompactView(
        actionNames: MutableList<String>,
        player: Player
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

    @ExperimentalStdlibApi
    override fun getActions(player: Player): List<String> {
        return buildList {
            add(CUSTOM_ACTION_DISLIKE)

            when {
                isPlaying(player) -> add(ACTION_PAUSE)
                else -> add(ACTION_PLAY)
            }

            add(CUSTOM_ACTION_NEXT)

            val id = currentRadioQueueHolder.currentMediaQueue[player.currentWindowIndex].id

            if (likedRadioStationsHolder.likedRadioStationsIds.contains(id)) {
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