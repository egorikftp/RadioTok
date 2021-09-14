package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.IPlayingStationsDataSource
import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.data.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PlayingStationsDataSource(private val api: Api) : IPlayingStationsDataSource {

    private var result: List<RadioEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.playingNow(limit = 100).also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}