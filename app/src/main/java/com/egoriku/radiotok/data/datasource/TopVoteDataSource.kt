package com.egoriku.radiotok.data.datasource

import com.egoriku.radiotok.common.datasource.ITopVoteDataSource
import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.data.Api
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