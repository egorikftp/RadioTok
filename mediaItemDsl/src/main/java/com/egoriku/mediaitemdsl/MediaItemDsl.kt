package com.egoriku.mediaitemdsl

import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.mediaitemdsl.mediaitem.MediaItemBuilder

inline fun mediaItem(
    flag: Int,
    body: MediaItemBuilder.() -> Unit
) = MediaItemBuilder(flag).apply(body).build()

inline fun playableMediaItem(
    body: MediaItemBuilder.() -> Unit
) = MediaItemBuilder(flag = MediaItem.FLAG_PLAYABLE).apply(body).build()

fun playableMediaItem(
    metadata: MediaMetadataCompat
) = MediaItem(metadata.description, MediaItem.FLAG_PLAYABLE)

inline fun browsableMediaItem(
    body: MediaItemBuilder.() -> Unit
) = MediaItemBuilder(flag = MediaItem.FLAG_BROWSABLE).apply(body).build()

fun browsableMediaItem(
    metadata: MediaMetadataCompat
) = MediaItem(metadata.description, MediaItem.FLAG_BROWSABLE)
