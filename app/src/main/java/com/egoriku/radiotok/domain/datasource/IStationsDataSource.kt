package com.egoriku.radiotok.domain.datasource

import com.egoriku.radiotok.data.entity.StationNetworkEntity

interface IStationsDataSource {

    suspend fun load(baseUrl: String): List<StationNetworkEntity>
}