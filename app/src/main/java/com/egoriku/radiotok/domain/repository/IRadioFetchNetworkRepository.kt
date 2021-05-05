package com.egoriku.radiotok.domain.repository

import com.egoriku.radiotok.common.model.RadioItemModel

interface IRadioFetchNetworkRepository {

    suspend fun load(): List<RadioItemModel>
}