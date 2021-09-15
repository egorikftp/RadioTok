package com.egoriku.radiotok.radioplayer.data.mediator

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.radioplayer.model.MediaPath

interface IRadioCacheMediator {

    suspend fun playSingle(id: String)

    suspend fun playNextRandom()

    suspend fun getMediaBrowserItemsBy(mediaPath: MediaPath): List<MediaBrowserCompat.MediaItem>

    suspend fun getMediaMetadataBy(mediaPath: MediaPath): MediaMetadataCompat

    suspend fun updatePlaylist(mediaPath: MediaPath)
}