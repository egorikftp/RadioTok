package com.egoriku.radiotok.exoplayer.data

import com.egoriku.radiotok.domain.model.RadioItemModel
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class LikedRadioStationsHolder {

    var likedRadioStations = mutableListOf<RadioItemModel>()
        private set

    var dislikedRadioStations = mutableListOf<RadioItemModel>()
        private set

    fun dislike(radioItemModel: RadioItemModel) {
        dislikedRadioStations.add(radioItemModel)
    }

    fun unlike(
        player: Player,
        radioItemModel: RadioItemModel,
        controlDispatcher: ControlDispatcher
    ) {
        likedRadioStations.remove(radioItemModel)

        controlDispatcher.dispatchSeekTo(player, player.currentWindowIndex, player.currentPosition)
    }

    fun unlike(
        playerNotificationManager: PlayerNotificationManager,
        radioItemModel: RadioItemModel
    ) {
        likedRadioStations.remove(radioItemModel)
        playerNotificationManager.invalidate()
    }

    fun like(
        player: Player,
        radioItemModel: RadioItemModel,
        controlDispatcher: ControlDispatcher
    ) {
        likedRadioStations.add(radioItemModel)

        controlDispatcher.dispatchSeekTo(player, player.currentWindowIndex, player.currentPosition)
    }

    fun like(
        playerNotificationManager: PlayerNotificationManager,
        radioItemModel: RadioItemModel
    ) {
        likedRadioStations.add(radioItemModel)
        playerNotificationManager.invalidate()
    }
}