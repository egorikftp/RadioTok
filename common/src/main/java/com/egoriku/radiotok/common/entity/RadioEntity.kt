package com.egoriku.radiotok.common.entity

import com.google.gson.annotations.SerializedName

data class RadioEntity(
    @SerializedName("name")
    val name: String,

    @SerializedName("stationuuid")
    val stationUuid: String,

    @SerializedName("url_resolved")
    val streamUrl: String,

    @SerializedName("homepage")
    val homePageUrl: String,

    @SerializedName("favicon")
    val icon: String,

    @SerializedName("countrycode")
    val countryCode: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("tags")
    val tags: String,

    @SerializedName("language")
    val language: String,

    @SerializedName("bitrate")
    val bitrate: Int,

    @SerializedName("codec")
    val codec: String,

    @SerializedName("hls")
    val hls: Long
)