package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat.MediaItem
import com.egoriku.radiotok.datasource.entity.RadioEntity

interface IMediaItemRepository {

    fun getRootItems(): List<MediaItem>
    fun getShuffleAndPlayItems(): List<MediaItem>

    fun getPersonalPlaylistsItems(): List<MediaItem>
    fun getLikedItems(): List<MediaItem>
    fun getLikedItemsTest(): List<RadioEntity>
    fun getRecentlyPlayedItems(): List<MediaItem>
    fun getDislikedItems(): List<MediaItem>

    fun getSmartPlaylistsItems(): List<MediaItem>
    fun getLocalItems(): List<MediaItem>
    fun getTopClicksItems(): List<MediaItem>
    fun getTopVoteItems(): List<MediaItem>
    fun getChangedLatelyItems(): List<MediaItem>
    fun getPlayingItems(): List<MediaItem>

    fun getCatalogItems(): List<MediaItem>
    fun getCatalogTags(): List<MediaItem>
    fun getCatalogCountries(): List<MediaItem>
    fun getCatalogLanguages(): List<MediaItem>
}