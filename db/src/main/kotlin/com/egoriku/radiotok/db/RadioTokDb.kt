package com.egoriku.radiotok.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.egoriku.radiotok.db.dao.StationDao
import com.egoriku.radiotok.db.entity.StationDbEntity

@Database(entities = [StationDbEntity::class], version = 1)
abstract class RadioTokDb: RoomDatabase() {

    abstract fun stationDao(): StationDao
}