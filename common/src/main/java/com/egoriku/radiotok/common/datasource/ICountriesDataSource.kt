package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.MetadataEntity

interface ICountriesDataSource {

    suspend fun load(): List<MetadataEntity>
}