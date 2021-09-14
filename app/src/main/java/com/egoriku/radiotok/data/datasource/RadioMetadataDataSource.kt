package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.IRadioMetadataDataSource
import com.egoriku.radiotok.data.Api
import com.egoriku.radiotok.data.metadata.RadioStationOverhaul
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class RadioMetadataDataSource(private val api: Api) : IRadioMetadataDataSource {

    override suspend fun loadByIds(ids: List<String>) = withContext(Dispatchers.IO) {
        runCatching {
            api.stationsById(ids = ids.joinToString(separator = ","))
                .map { RadioStationOverhaul.tryToFix(it) }
        }.getOrDefault(emptyList())
    }
}