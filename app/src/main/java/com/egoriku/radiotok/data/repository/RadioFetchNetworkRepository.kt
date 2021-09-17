package com.egoriku.radiotok.data.repository

import com.egoriku.radiotok.datasource.datasource.IAllStationsDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository

class RadioFetchNetworkRepository(
    private val allStationsDataSource: IAllStationsDataSource
) : IRadioFetchNetworkRepository {

    override suspend fun load(): List<RadioEntity> = allStationsDataSource.loadAll()
}
