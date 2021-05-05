package com.egoriku.radiotok.radioplayer.data

import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.ext.isHsl
import com.egoriku.radiotok.radioplayer.ext.mediaUri
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class CurrentRadioQueueHolder(
    private val radioCacheMediator: IRadioCacheMediator,
    private val defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory
) {

    val currentMediaSource = ConcatenatingMediaSource()
    val currentMediaQueue = mutableListOf<MediaMetadataCompat>()

    suspend fun switchToRandomRadios() {
        currentMediaSource.clear()

        val newQueue = radioCacheMediator.getMediaMetadataBy(mediaPath = MediaPath.RandomRadio)

        currentMediaQueue.clear()
        currentMediaQueue.addAll(newQueue)

        currentMediaSource.addMediaSources(newQueue.toMediaSource())
    }

    suspend fun switchToLikedRadios() {
        currentMediaSource.clear()

        val newQueue = radioCacheMediator.getMediaMetadataBy(mediaPath = MediaPath.LikedRadio)

        currentMediaQueue.clear()
        currentMediaQueue.addAll(newQueue)

        currentMediaSource.addMediaSources(newQueue.toMediaSource())
    }

    private fun List<MediaMetadataCompat>.toMediaSource() = map {
        it.toMediaSource()
    }

    private fun MediaMetadataCompat.toMediaSource(): MediaSource {
        val mediaItem = MediaItem.fromUri(mediaUri)

        return when {
            isHsl -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(mediaItem)
            else -> ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(mediaItem)
        }
    }
}