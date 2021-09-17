package com.egoriku.radiotok.datasource.intertal.datasource

import com.egoriku.radiotok.datasource.datasource.IAllStationsDataSource
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AllStationsDataSource(private val api: Api) : IAllStationsDataSource {

    override suspend fun loadAll() = withContext(Dispatchers.IO) {
        runCatching { api.allStations() }.getOrThrow()
    }
}