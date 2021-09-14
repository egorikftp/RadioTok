package com.egoriku.radiotok.data.metadata

import com.egoriku.radiotok.common.entity.RadioEntity

object RadioStationOverhaul {

    private val data = listOf(
        Metadata(
            id = "732bae83-1a25-11e8-a334-52543be04c81",
            logoUrl = "https://dbs.radioline.fr/pictures/radio_0f07314cc317183b5441bab508760d9c/logo200.jpg"
        ),
        Metadata(
            id = "10f7b21c-74ed-4527-a59f-958f4d2a62c2",
            logoUrl = "https://www.radio1.hu/wp-content/themes/radio1/assets/dist/img/logo.png"
        )
    )

    fun tryToFix(radioEntity: RadioEntity): RadioEntity {
        val metadata = data.find { metadata ->
            metadata.id == radioEntity.stationUuid
        }

        return when (metadata) {
            null -> radioEntity
            else -> radioEntity.copy(icon = metadata.logoUrl)
        }
    }
}