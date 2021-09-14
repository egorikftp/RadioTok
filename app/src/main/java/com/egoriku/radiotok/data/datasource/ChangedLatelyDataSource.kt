package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.IChangedLatelyDataSource
import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.data.Api
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