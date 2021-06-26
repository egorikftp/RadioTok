package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

interface IMediaItemRepository {

    fun getRootItems(): List<MediaBrowserCompat.MediaItem>

    suspend fun getRandomItems(): MediaMetadataCompat
}