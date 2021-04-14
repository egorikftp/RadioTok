package com.egoriku.radiotok.exoplayer.data

import com.egoriku.radiotok.domain.model.RadioItemModel
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class LikedRadioStationsHolder {

    var likedRadioStations = mutableListOf<RadioItemModel>()
        private set

    var dislikedRadioStations = mutableListOf<RadioItemModel>()
        private set

    fun dislike(radioItemModel: RadioItemModel) {
        dislikedRadioStations.add(radioItemModel)
    }

    fun unlike(radioItemModel: RadioItemModel) {
        likedRadioStations.remove(radioItemModel)
    }

    fun unlike(
        playerNotificationManager: PlayerNotificationManager,
        radioItemModel: RadioItemModel
    ) {
        likedRadioStations.remove(radioItemModel)
        playerNotificationManager.invalidate()
    }

    fun like(radioItemModel: RadioItemModel) {
        likedRadioStations.add(radioItemModel)
    }

    fun like(
        playerNotificationManager: PlayerNotificationManager,
        radioItemModel: RadioItemModel
    ) {
        likedRadioStations.add(radioItemModel)
        playerNotificationManager.invalidate()
    }
}