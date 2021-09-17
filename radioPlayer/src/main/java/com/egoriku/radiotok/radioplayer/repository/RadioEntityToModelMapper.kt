package com.egoriku.radiotok.radioplayer.repository

import com.egoriku.radiotok.common.ext.IMapper
import com.egoriku.radiotok.common.mapper.MetadataBuilder
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.datasource.entity.RadioEntity

class RadioEntityToModelMapper : IMapper<RadioEntity, RadioItemModel> {

    override fun invoke(radioEntity: RadioEntity): RadioItemModel =
        RadioItemModel(
            id = radioEntity.stationUuid,
            name = radioEntity.name,
            streamUrl = radioEntity.streamUrl,
            icon = radioEntity.icon,
            hls = radioEntity.hls,
            metadata = MetadataBuilder.build(
                countryCode = radioEntity.countryCode,
                tags = radioEntity.tags
            )
        )
}