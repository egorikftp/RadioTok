package com.egoriku.radiotok.datasource.datasource

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IAllStationsDataSource {

    suspend fun loadAll(): List<RadioEntity>
}