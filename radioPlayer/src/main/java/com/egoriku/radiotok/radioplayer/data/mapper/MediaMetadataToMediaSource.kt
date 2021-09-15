package com.egoriku.radiotok.radioplayer.data.mapper

import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.radioplayer.ext.isHls
import com.egoriku.radiotok.radioplayer.ext.mediaUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

fun List<MediaMetadataCompat>.toMediaSource(
    defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory
) = map { metadataCompat ->
    metadataCompat.toMediaSource(defaultHttpDataSourceFactory)
}

private fun MediaMetadataCompat.toMediaSource(
    defaultHttpDataSourceFactory: DefaultHttpDataSource.Factory
): MediaSource {
    val mediaItem = MediaItem.fromUri(mediaUri)

    return when {
        isHls -> HlsMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(mediaItem)
        else -> ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(mediaItem)
    }
}