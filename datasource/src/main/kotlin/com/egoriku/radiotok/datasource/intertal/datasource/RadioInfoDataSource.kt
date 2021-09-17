package com.egoriku.radiotok.datasource.intertal.datasource

import com.egoriku.radiotok.datasource.datasource.IRadioInfoDataSource
import com.egoriku.radiotok.datasource.intertal.api.Api
import com.egoriku.radiotok.datasource.intertal.repair.RadioStationRepair
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RadioInfoDataSource(private val api: Api) : IRadioInfoDataSource {

    override suspend fun loadByIds(ids: List<String>) = withContext(Dispatchers.IO) {
        runCatching {
            api.stationsById(ids = ids.joinToString(separator = ","))
                .map { RadioStationRepair.tryToFix(it) }
        }.getOrDefault(emptyList())
    }
}