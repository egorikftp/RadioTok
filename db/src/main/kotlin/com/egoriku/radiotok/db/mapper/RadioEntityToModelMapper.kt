package com.egoriku.radiotok.db.mapper

import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.common.ext.IMapper
import com.egoriku.radiotok.common.ext.toFlagEmoji
import com.egoriku.radiotok.common.model.RadioItemModel

class RadioEntityToModelMapper : IMapper<RadioEntity, RadioItemModel> {

    override fun invoke(radioEntity: RadioEntity): RadioItemModel =
        RadioItemModel(
            id = radioEntity.stationUuid,
            name = radioEntity.name,
            streamUrl = radioEntity.streamUrl,
            icon = radioEntity.icon,
            hls = radioEntity.hls,
            metadata = buildMetadata(radioEntity)
        )

    @OptIn(ExperimentalStdlibApi::class)
    private fun buildMetadata(radioEntity: RadioEntity) = buildList {
        if (radioEntity.countryCode.isNotEmpty()) {
            add(radioEntity.countryCode.toFlagEmoji)
        }

        if (radioEntity.tags.isNotEmpty()) {
            add(radioEntity.tags.replace(",", ", "))
        }
    }.joinToString(" / ")
}