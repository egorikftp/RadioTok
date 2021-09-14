package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.RadioEntity

interface ILocalStationsDataSource {

    suspend fun load(): List<RadioEntity>
}