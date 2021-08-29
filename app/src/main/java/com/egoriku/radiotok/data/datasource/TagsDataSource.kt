package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.ITagsDataSource
import com.egoriku.radiotok.common.entity.MetadataEntity
import com.egoriku.radiotok.data.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TagsDataSource(private val api: Api) : ITagsDataSource {

    private var result: List<MetadataEntity>? = null

    private val grouped: Map<String, List<MetadataEntity>>
        get() = result
            ?.groupBy { it.name.substring(0, 1) }
            ?: emptyMap()

    override suspend fun getGroupedTags(): Map<String, List<MetadataEntity>> {
        load()

        return grouped
    }

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.allTags().also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}