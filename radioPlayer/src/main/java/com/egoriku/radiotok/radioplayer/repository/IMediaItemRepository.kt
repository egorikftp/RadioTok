package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.model.RadioItemModel

interface IMediaItemRepository {

    fun getRootItems(): List<MediaBrowserCompat.MediaItem>

    fun getRandomItems(items: List<RadioItemModel>): List<MediaMetadataCompat>
}