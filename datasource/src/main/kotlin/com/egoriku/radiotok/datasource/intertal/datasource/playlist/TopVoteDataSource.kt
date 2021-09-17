package com.egoriku.radiotok.datasource.intertal.datasource.playlist

import com.egoriku.radiotok.datasource.datasource.playlist.ITopVoteDataSource
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.api.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class TopVoteDataSource(private val api: Api) : ITopVoteDataSource {

    private var result: List<RadioEntity>? = null

    override suspend fun load() = runCatching {
        withContext(Dispatchers.IO) {
            result ?: api.topVote(limit = 100).also {
                result = it
            }
        }
    }.getOrDefault(emptyList())
}