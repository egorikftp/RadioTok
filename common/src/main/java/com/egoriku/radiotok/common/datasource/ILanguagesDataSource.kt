package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.MetadataEntity

interface ILanguagesDataSource {

    suspend fun load(): List<MetadataEntity>
}