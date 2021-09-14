package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.RadioEntity

interface ITopVoteDataSource {

    suspend fun load(): List<RadioEntity>
}