package com.egoriku.radiotok.domain.datasource

import com.egoriku.radiotok.data.entity.StationEntity

interface IStationsDataSource {

    suspend fun load(baseUrl: String): List<StationEntity>
}