package com.egoriku.radiotok.datasource.datasource

import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IRadioInfoDataSource {

    suspend fun loadByIds(ids: List<String>): List<RadioEntity>
}