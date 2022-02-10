package com.egoriku.radiotok.datasource.intertal.datasource.metadata

import com.egoriku.radiotok.datasource.datasource.metadata.ILanguagesDataSource
import com.egoriku.radiotok.datasource.entity.MetadataEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LanguagesDataSource(private val api: Api) : ILanguagesDataSource {

    private var result: List<MetadataEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.allLanguages().also {
                result = it
            }
        }
    }.getOrDefault(emptyList())

    override suspend fun loadPortion(size: Int) = runCatching {
        load()
        requireNotNull(result).subList(0, size)
    }.getOrDefault(emptyList())
}