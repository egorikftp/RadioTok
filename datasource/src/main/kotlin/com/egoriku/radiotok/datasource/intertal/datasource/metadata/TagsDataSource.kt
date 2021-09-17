package com.egoriku.radiotok.datasource.intertal.datasource.metadata

import com.egoriku.radiotok.datasource.datasource.metadata.ITagsDataSource
import com.egoriku.radiotok.datasource.entity.MetadataEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TagsDataSource(private val api: Api) : ITagsDataSource {

    private var result: List<MetadataEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.allTags().also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}