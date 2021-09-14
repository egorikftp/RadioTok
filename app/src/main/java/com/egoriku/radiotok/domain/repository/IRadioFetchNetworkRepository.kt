package com.egoriku.radiotok.domain.repository

import com.egoriku.radiotok.common.entity.RadioEntity

interface IRadioFetchNetworkRepository {

    suspend fun load(): List<RadioEntity>
}