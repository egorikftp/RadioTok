package com.egoriku.radiotok.data.repository

import com.egoriku.radiotok.data.entity.StationNetworkEntity
import com.egoriku.radiotok.domain.datasource.IRadioServerDataSource
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository

class RadioFetchNetworkRepository(
    private val radioServerDataSource: IRadioServerDataSource,
    private val stationsDataSource: IStationsDataSource
) : IRadioFetchNetworkRepository {

    override suspend fun load(): List<StationNetworkEntity> {
        val loadStations = radioServerDataSource.loadRadioServices()
        val baseUrl = loadStations.random()

        return stationsDataSource.load(baseUrl = baseUrl)
    }
}
