package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

interface IMediaItemRepository {

    fun getRootItems(): List<MediaBrowserCompat.MediaItem>
    fun getForYouItems(): List<MediaBrowserCompat.MediaItem>

    suspend fun getRandomItem(): MediaMetadataCompat

    suspend fun getLikedItem(): MediaMetadataCompat
}