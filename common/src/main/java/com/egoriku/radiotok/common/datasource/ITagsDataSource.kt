package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.MetadataEntity

interface ITagsDataSource {

    suspend fun load(): List<MetadataEntity>

    suspend fun getGroupedTags(): Map<String, List<MetadataEntity>>
}