package com.egoriku.radiotok.domain.datasource

import com.egoriku.radiotok.common.entity.RadioEntity

interface IStationsDataSource {

    suspend fun loadAll(): List<RadioEntity>
}