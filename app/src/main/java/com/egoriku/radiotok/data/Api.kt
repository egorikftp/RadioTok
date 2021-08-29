package com.egoriku.radiotok.data

import com.egoriku.radiotok.common.entity.MetadataEntity
import com.egoriku.radiotok.data.entity.StationNetworkEntity
import retrofit2.http.GET

interface Api {

    @GET("json/stations")
    suspend fun allStations(): List<StationNetworkEntity>

    @GET("json/countries")
    suspend fun allCountries(): List<MetadataEntity>

    @GET("json/countrycodes")
    suspend fun allCountryCodes(): List<MetadataEntity>

    @GET("json/languages")
    suspend fun allLanguages(): List<MetadataEntity>

    @GET("json/tags")
    suspend fun allTags(): List<MetadataEntity>
}