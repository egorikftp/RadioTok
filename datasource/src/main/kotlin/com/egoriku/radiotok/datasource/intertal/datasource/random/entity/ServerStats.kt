package com.egoriku.radiotok.datasource.intertal.datasource.random.entity

import com.google.gson.annotations.SerializedName

data class ServerStats(

    @SerializedName("supported_version")
    val supportedVersion: Int,

    @SerializedName("software_version")
    val softwareVersion: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("stations")
    val stations: Int,

    @SerializedName("stations_broken")
    val stationsBroken: Int,

    @SerializedName("tags")
    val tags: Int,

    @SerializedName("clicks_last_hour")
    val clicksLastHour: Int,

    @SerializedName("clicks_last_day")
    val clicksLastDay: Int,

    @SerializedName("languages")
    val languages: Int,

    @SerializedName("countries")
    val countries: Int
)