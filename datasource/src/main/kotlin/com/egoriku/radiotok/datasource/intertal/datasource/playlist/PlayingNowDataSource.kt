package com.egoriku.radiotok.datasource.intertal.datasource.playlist

import com.egoriku.radiotok.datasource.datasource.playlist.IPlayingNowDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class PlayingNowDataSource(private val api: Api) : IPlayingNowDataSource {

    private var result: List<RadioEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.playingNow(limit = 100).also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}