package com.egoriku.radiotok.datasource.intertal.datasource.metadata

import com.egoriku.radiotok.datasource.datasource.metadata.ICountriesDataSource
import com.egoriku.radiotok.datasource.entity.MetadataEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CountriesDataSource(private val api: Api) : ICountriesDataSource {

    private var result: List<MetadataEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.allCountries().also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}