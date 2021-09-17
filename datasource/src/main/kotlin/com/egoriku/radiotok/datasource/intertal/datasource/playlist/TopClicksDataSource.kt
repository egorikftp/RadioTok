package com.egoriku.radiotok.datasource.intertal.datasource.playlist

import com.egoriku.radiotok.datasource.datasource.playlist.ITopClicksDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TopClicksDataSource(private val api: Api) : ITopClicksDataSource {

    private var result: List<RadioEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.topClicks(limit = 100).also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}