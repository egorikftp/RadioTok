package com.egoriku.radiotok.datasource.intertal.datasource.playlist

import com.egoriku.radiotok.datasource.datasource.playlist.IChangedLatelyDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ChangedLatelyDataSource(private val api: Api) : IChangedLatelyDataSource {

    private var result: List<RadioEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.changedLately(limit = 100).also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}