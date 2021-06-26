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

    @Query("SELECT COUNT(*) FROM stationdbentity")
    suspend fun getStationsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<StationDbEntity>)
}