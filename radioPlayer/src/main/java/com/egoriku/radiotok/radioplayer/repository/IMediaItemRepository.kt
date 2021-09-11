package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

interface IMediaItemRepository {

    fun getRootItems(): List<MediaBrowserCompat.MediaItem>
    fun getShuffleAndPlayItems(): List<MediaBrowserCompat.MediaItem>

    fun getPersonalPlaylistsItems(): List<MediaBrowserCompat.MediaItem>
    fun getLikedItems(): List<MediaBrowserCompat.MediaItem>
    fun getRecentlyPlayedItems(): List<MediaBrowserCompat.MediaItem>

    fun getSmartPlaylistsItems(): List<MediaBrowserCompat.MediaItem>

    fun getCatalogItems(): List<MediaBrowserCompat.MediaItem>
    fun getCatalogTags(): List<MediaBrowserCompat.MediaItem>
    fun getCatalogCountries(): List<MediaBrowserCompat.MediaItem>
    fun getCatalogLanguages(): List<MediaBrowserCompat.MediaItem>

    suspend fun getRandomItem(): MediaMetadataCompat

    suspend fun getLikedItem(): MediaMetadataCompat
}