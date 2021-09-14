package com.egoriku.radiotok.data

import com.egoriku.radiotok.common.entity.MetadataEntity
import com.egoriku.radiotok.common.entity.RadioEntity
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
}