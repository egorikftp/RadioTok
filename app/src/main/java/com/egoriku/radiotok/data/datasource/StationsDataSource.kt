package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.data.Api
import com.egoriku.radiotok.domain.datasource.IStationsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class StationsDataSource(private val api: Api) : IStationsDataSource {

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        runCatching { api.allStations() }.getOrThrow()
    }
}