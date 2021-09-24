package com.egoriku.radiotok.radioplayer.data

import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.data.mapper.toMediaSource
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.model.MediaPath.ShuffleAndPlayRoot
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class CurrentRadioQueueHolder(
    private val defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory
) {
    var currentMediaSource = ConcatenatingMediaSource()
        private set

    var currentMediaPath: MediaPath = MediaPath.Root
        private set

    var radioStations = mutableListOf<MediaMetadataCompat>()
        private set

    fun isSingle() = currentMediaPath == MediaPath.Single

    fun isRandomRadio() = currentMediaPath == ShuffleAndPlayRoot.ShuffleRandom ||
            currentMediaPath == ShuffleAndPlayRoot.ShuffleLiked

    fun getMediaMetadataOrNull(position: Int): MediaMetadataCompat? =
        radioStations.getOrNull(position)

    fun updateQueue(mediaPath: MediaPath, stations: List<MediaMetadataCompat>) {
        logD("updateQueue: size = ${stations.size}")

        currentMediaPath = mediaPath

        radioStations.clear()
        radioStations.addAll(stations)

        currentMediaSource.clear()
        currentMediaSource.addMediaSources(stations.toMediaSource(defaultHttpDataSourceFactory))
    }
}