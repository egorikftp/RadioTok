package com.egoriku.radiotok.radioplayer.data

import com.egoriku.radiotok.db.RadioTokDb
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.runBlocking

class RadioStateMediator(private val radioTokDb: RadioTokDb) {

    fun exclude(id: String) = runBlocking {
        radioTokDb.stationDao().toggleExcludedState(stationId = id)
    }

    fun isLiked(id: String) = runBlocking {
        radioTokDb.stationDao().isStationLiked(stationId = id)
    }

    fun toggleLiked(
        id: String,
        playerNotificationManager: PlayerNotificationManager? = null
    ) {
        runBlocking {
            radioTokDb.stationDao().toggleLikedState(stationId = id)
        }

        playerNotificationManager?.invalidate()
    }
}