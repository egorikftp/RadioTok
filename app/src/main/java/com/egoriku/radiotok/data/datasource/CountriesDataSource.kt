package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.ICountriesDataSource
import com.egoriku.radiotok.common.entity.MetadataEntity
import com.egoriku.radiotok.data.Api
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