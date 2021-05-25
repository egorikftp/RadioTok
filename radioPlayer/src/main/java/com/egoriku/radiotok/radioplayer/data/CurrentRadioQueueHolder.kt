package com.egoriku.radiotok.radioplayer.data

import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.radioplayer.ext.isHls
import com.egoriku.radiotok.radioplayer.ext.mediaUri
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlin.properties.Delegates

class CurrentRadioQueueHolder(
    private val defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory
) {
    var currentMediaMetadata: MediaMetadataCompat? = null
        private set

    var currentMediaSource: MediaSource by Delegates.notNull()
        private set

    var currentPath: MediaPath = MediaPath.Root

    fun set(item: MediaMetadataCompat) {
        logD("set")

        currentMediaMetadata = item
        currentMediaSource = item.toMediaSource()
    }

    private fun MediaMetadataCompat.toMediaSource(): MediaSource {
        val mediaItem = MediaItem.fromUri(mediaUri)

        return when {
            isHls -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(mediaItem)
            else -> ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(mediaItem)
        }
    }
}