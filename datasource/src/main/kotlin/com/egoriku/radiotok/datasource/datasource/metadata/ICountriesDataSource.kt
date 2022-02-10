package com.egoriku.radiotok.datasource.datasource.metadata

import com.egoriku.radiotok.datasource.entity.MetadataEntity

interface ICountriesDataSource {

    suspend fun load(): List<MetadataEntity>

    suspend fun loadPortion(size: Int): List<MetadataEntity>
}