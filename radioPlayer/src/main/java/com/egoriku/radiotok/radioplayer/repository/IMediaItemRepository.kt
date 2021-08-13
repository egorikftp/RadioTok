package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

interface IMediaItemRepository {

    fun getRootItems(): List<MediaBrowserCompat.MediaItem>
    fun getShuffleAndPlayItems(): List<MediaBrowserCompat.MediaItem>
    fun getPersonalPlaylistsItems(): List<MediaBrowserCompat.MediaItem>
    fun getSmartPlaylistsItems(): List<MediaBrowserCompat.MediaItem>
    fun getCatalogItems(): List<MediaBrowserCompat.MediaItem>

    suspend fun getRandomItem(): MediaMetadataCompat

    suspend fun getLikedItem(): MediaMetadataCompat
}