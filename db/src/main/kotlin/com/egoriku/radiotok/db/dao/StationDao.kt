package com.egoriku.radiotok.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egoriku.radiotok.db.entity.StationDbEntity

@Dao
interface StationDao {

    @Query("SELECT * FROM stationdbentity WHERE isExcluded = 0 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomStation(): StationDbEntity

    @Query("SELECT * FROM stationdbentity WHERE isExcluded = 0 AND isLiked = 1 ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomLikedStation(): StationDbEntity

    @Query("SELECT COUNT(*) FROM stationdbentity")
    suspend fun getStationsCount(): Int

    @Query("SELECT COUNT(*) FROM stationdbentity WHERE isLiked = 1")
    suspend fun likedStationsCount(): Int

    @Query("SELECT isLiked FROM stationdbentity WHERE stationUuid = :stationId")
    suspend fun isStationLiked(stationId: String): Boolean

    @Query("UPDATE stationdbentity SET isLiked = NOT isLiked WHERE stationUuid = :stationId")
    suspend fun toggleLikedState(stationId: String)

    @Query("UPDATE stationdbentity SET isExcluded = NOT isExcluded WHERE stationUuid = :stationId")
    suspend fun toggleExcludedState(stationId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<StationDbEntity>)
}