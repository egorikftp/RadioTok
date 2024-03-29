package com.egoriku.radiotok.datasource.entity

import com.google.gson.annotations.SerializedName

data class MetadataEntity(

    @SerializedName("name")
    val name: String,

    @SerializedName("stationcount")
    val count: Int
)
