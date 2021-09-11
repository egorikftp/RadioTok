package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.ILanguagesDataSource
import com.egoriku.radiotok.common.entity.MetadataEntity
import com.egoriku.radiotok.data.Api
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
}