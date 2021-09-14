package com.egoriku.radiotok.data.repository

import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository

class RadioFetchNetworkRepository(
    private val stationsDataSource: IStationsDataSource
) : IRadioFetchNetworkRepository {

    override suspend fun load(): List<RadioEntity> = stationsDataSource.loadAll()
}
