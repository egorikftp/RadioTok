package com.egoriku.radiotok.datasource.datasource.playlist

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IPlayingNowDataSource {

    suspend fun load(): List<RadioEntity>
}