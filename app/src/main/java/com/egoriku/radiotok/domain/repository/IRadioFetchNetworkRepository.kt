package com.egoriku.radiotok.domain.repository

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IRadioFetchNetworkRepository {

    suspend fun load(): List<RadioEntity>
}