package com.egoriku.radiotok.common.datasource

import com.egoriku.radiotok.common.entity.RadioEntity

interface IChangedLatelyDataSource {

    suspend fun load(): List<RadioEntity>
}