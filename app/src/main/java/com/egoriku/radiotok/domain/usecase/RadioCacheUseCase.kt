package com.egoriku.radiotok.domain.usecase

import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.data.mapper.NetworkStationToDbMapper
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository

class RadioCacheUseCase(
    private val radioTokDb: RadioTokDb,
    private val radioFetchNetworkRepository: IRadioFetchNetworkRepository
) : IRadioCacheUseCase {

    override suspend fun preCacheStations() {
        if (radioTokDb.stationDao().getStationsCount() == 0) {
            logD("preCacheStations()")
            loadAndCache()
        }
    }

    private suspend fun loadAndCache() {
        val stations = radioFetchNetworkRepository
            .load()
            .map(transform = NetworkStationToDbMapper())

        radioTokDb.stationDao().insertAll(stations)
    }
}

interface IRadioCacheUseCase {

    suspend fun preCacheStations()
}