package com.egoriku.radiotok.radioplayer.data.mediator

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.radioplayer.model.MediaPath

interface IRadioCacheMediator {

    suspend fun getAllRadioStations(): List<RadioItemModel>

    suspend fun getMediaBrowserItemsBy(mediaPath: MediaPath): List<MediaBrowserCompat.MediaItem>

    suspend fun getMediaMetadataBy(mediaPath: MediaPath): List<MediaMetadataCompat>
}