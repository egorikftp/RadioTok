package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.datasource.IEntitySourceFactory
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.radioplayer.ext.from

internal class MediaMetadataRepository(
    private val radioTokDb: RadioTokDb,
    private val entitySourceFactory: IEntitySourceFactory,
) : IMediaMetadataRepository {

    private val entityMapper = RadioEntityToModelMapper()

    override suspend fun getRandomItem(): MediaMetadataCompat {
        val id = radioTokDb.stationDao().getRandomStationId()

        return loadByStationId(id)
    }

    override suspend fun getLikedItem(): MediaMetadataCompat {
        val id = radioTokDb.stationDao().getRandomLikedStationId()

        return loadByStationId(id)
    }

    override suspend fun loadByStationId(id: String) = runCatching {
        logD("loadByStationId: $id")
        val stationById = entitySourceFactory.loadByIds(listOf(id)).first()

        MediaMetadataCompat.Builder().from(
            itemModel = entityMapper.invoke(stationById)
        ).build()
    }.getOrNull() ?: getRandomItem()
}