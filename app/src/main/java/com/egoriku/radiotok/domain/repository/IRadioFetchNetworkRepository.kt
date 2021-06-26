package com.egoriku.radiotok.domain.repository

import com.egoriku.radiotok.data.entity.StationNetworkEntity

interface IRadioFetchNetworkRepository {

    suspend fun load(): List<StationNetworkEntity>
}