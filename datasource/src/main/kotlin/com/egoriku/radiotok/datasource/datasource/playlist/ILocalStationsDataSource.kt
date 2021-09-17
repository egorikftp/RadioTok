package com.egoriku.radiotok.datasource.datasource.playlist

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface ILocalStationsDataSource {

    suspend fun load(): List<RadioEntity>
}