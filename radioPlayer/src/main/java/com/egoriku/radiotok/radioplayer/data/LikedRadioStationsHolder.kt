package com.egoriku.radiotok.radioplayer.data

import com.google.android.exoplayer2.ui.PlayerNotificationManager

class LikedRadioStationsHolder {

    var likedRadioStationsIds = mutableListOf<String>()
        private set

    var dislikedRadioStationsIds = mutableListOf<String>()
        private set

    fun dislike(id: String) {
        dislikedRadioStationsIds.add(id)
    }

    fun unlike(id: String) {
        likedRadioStationsIds.remove(id)
    }

    fun unlike(
        playerNotificationManager: PlayerNotificationManager,
        id: String
    ) {
        likedRadioStationsIds.remove(id)
        playerNotificationManager.invalidate()
    }

    fun like(id: String) {
        likedRadioStationsIds.add(id)
    }

    fun like(
        playerNotificationManager: PlayerNotificationManager,
        id: String
    ) {
        likedRadioStationsIds.add(id)
        playerNotificationManager.invalidate()
    }
}