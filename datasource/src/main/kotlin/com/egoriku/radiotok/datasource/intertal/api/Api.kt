package com.egoriku.radiotok.datasource.intertal.api

import com.egoriku.radiotok.datasource.entity.MetadataEntity
import com.egoriku.radiotok.datasource.entity.RadioEntity
import com.egoriku.radiotok.datasource.intertal.datasource.random.entity.ServerStats
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("json/stations/byuuid")
    suspend fun stationsById(@Query("uuids") ids: String): List<RadioEntity>

    @GET("json/stations")
    suspend fun allStations(): List<RadioEntity>

    @GET("json/countries")
    suspend fun allCountries(): List<MetadataEntity>

    @GET("json/stations/topclick")
    suspend fun topClicks(@Query("limit") limit: Int): List<RadioEntity>

    @GET("json/stations/bycountrycodeexact/{countryCode}")
    suspend fun byCountyCode(@Path("countryCode") countryCode: String): List<RadioEntity>

    @GET("json/stations/lastchange")
    suspend fun changedLately(@Query("limit") limit: Int): List<RadioEntity>

    @GET("json/stations/lastclick")
    suspend fun playingNow(@Query("limit") limit: Int): List<RadioEntity>

    @GET("json/stations/topvote")
    suspend fun topVote(@Query("limit") limit: Int): List<RadioEntity>

    @GET("json/languages")
    suspend fun allLanguages(
        @Query("hidebroken") isHideBroken: Boolean = true
    ): List<MetadataEntity>

    @GET("json/tags")
    suspend fun allTags(): List<MetadataEntity>

    @GET("json/stats")
    suspend fun stats(): ServerStats
}