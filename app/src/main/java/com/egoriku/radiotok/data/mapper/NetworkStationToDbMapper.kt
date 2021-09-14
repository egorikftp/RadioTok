package com.egoriku.radiotok.data.mapper

import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.common.ext.IMapper
import com.egoriku.radiotok.db.entity.StationDbEntity

class NetworkStationToDbMapper : IMapper<RadioEntity, StationDbEntity> {

    override fun invoke(networkEntity: RadioEntity) =
        StationDbEntity(
            stationUuid = networkEntity.stationUuid,
            name = networkEntity.name,
            streamUrl = networkEntity.streamUrl,
            icon = networkEntity.icon,
            hls = networkEntity.hls,
            countryCode = networkEntity.countryCode,
            tags = networkEntity.tags,
            isLiked = false,
            isExcluded = false
        )
}