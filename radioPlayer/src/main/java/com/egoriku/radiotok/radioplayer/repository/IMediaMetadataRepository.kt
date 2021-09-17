package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaMetadataCompat

interface IMediaMetadataRepository {

    suspend fun getRandomItem(): MediaMetadataCompat
    suspend fun getLikedItem(): MediaMetadataCompat
    suspend fun loadByStationId(id: String): MediaMetadataCompat
}