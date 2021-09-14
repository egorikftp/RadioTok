package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.RadioEntity

interface IRadioMetadataDataSource {

    suspend fun loadByIds(ids: List<String>): List<RadioEntity>
}