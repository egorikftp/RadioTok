package com.egoriku.radiotok.datasource.datasource.playlist

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IChangedLatelyDataSource {

    suspend fun load(): List<RadioEntity>
}