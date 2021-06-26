package com.egoriku.radiotok.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "stationUuid") val stationUuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "streamUrl") val streamUrl: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "hls") val hls: Long,
    @ColumnInfo(name = "countryCode") val countryCode: String,
    @ColumnInfo(name = "tags") val tags: String,
    @ColumnInfo(name = "isLiked") val isLiked: Boolean,
    @ColumnInfo(name = "isExcluded") val isExcluded: Boolean,
)
