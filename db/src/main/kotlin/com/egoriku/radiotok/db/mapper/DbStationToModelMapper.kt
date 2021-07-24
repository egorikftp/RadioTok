package com.egoriku.radiotok.db.mapper

import com.egoriku.radiotok.common.ext.IMapper
import com.egoriku.radiotok.common.ext.toFlagEmoji
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.db.entity.StationDbEntity

class DbStationToModelMapper : IMapper<StationDbEntity, RadioItemModel> {

    override fun invoke(dbEntity: StationDbEntity) =
        RadioItemModel(
            id = dbEntity.stationUuid,
            name = dbEntity.name,
            streamUrl = dbEntity.streamUrl,
            icon = dbEntity.icon,
            hls = dbEntity.hls,
            metadata = buildMetadata(dbEntity)
        )

    @OptIn(ExperimentalStdlibApi::class)
    private fun buildMetadata(entity: StationDbEntity) = buildList {
        if (entity.countryCode.isNotEmpty()) {
            add(entity.countryCode.toFlagEmoji)
        }

        if (entity.tags.isNotEmpty()) {
            add(entity.tags.replace(",", ", "))
        }
    }.joinToString(" / ")
}