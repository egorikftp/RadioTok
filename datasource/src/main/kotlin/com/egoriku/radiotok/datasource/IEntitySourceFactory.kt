package com.egoriku.radiotok.datasource

import com.egoriku.radiotok.datasource.entity.MetadataEntity
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.entity.params.MetadataParams
import com.egoriku.radiotok.datasource.entity.params.PlaylistParams

interface IEntitySourceFactory {

    suspend fun loadBy(params: PlaylistParams): List<RadioEntity>

    suspend fun loadBy(params: MetadataParams): List<MetadataEntity>

    suspend fun loadByIds(ids: List<String>): List<RadioEntity>
}