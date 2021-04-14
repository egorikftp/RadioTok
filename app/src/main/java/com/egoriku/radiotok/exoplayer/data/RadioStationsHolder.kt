package com.egoriku.radiotok.exoplayer.data

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.util.Log
import androidx.core.net.toUri
import com.egoriku.radiotok.domain.IRadioFetchUseCase
import com.egoriku.radiotok.domain.model.RadioItemModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

fun RadioItemModel.toMediaDescription() = MediaDescriptionCompat.Builder()
    .setMediaUri(streamUrl.toUri())
    .setTitle(name)
    .setSubtitle(name)
    .setMediaId(id)
    .setIconUri(icon.toUri())
    .build()


class RadioStationsHolder(
    private val radioFetchUseCase: IRadioFetchUseCase
) {

    val allRadioStations: List<RadioItemModel>
        get() = radioFetchUseCase.radios

    private var _currentRadioStation: RadioItemModel = RadioItemModel()
    val currentRadioStation: RadioItemModel
        get() = _currentRadioStation

    fun randomRadioModel(): RadioItemModel {
        _currentRadioStation = allRadioStations.random()

        return currentRadioStation
    }

    fun asMediaSource(
        dataSourceFactory: DefaultHttpDataSource.Factory,
        radioModel: RadioItemModel
    ): MediaSource {
        val mediaItem = MediaItem.fromUri(radioModel.streamUrl)

        return when {
            radioModel.isHsl -> HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
            else -> ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mediaItem)
        }
    }

    fun asMediaItems(): List<MediaBrowserCompat.MediaItem> {
        Log.d("kek", "asMediaItems")

        return allRadioStations.map { model ->
            MediaBrowserCompat.MediaItem(
                model.toMediaDescription(),
                MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
            )
        }.subList(0, 50)
    }
}