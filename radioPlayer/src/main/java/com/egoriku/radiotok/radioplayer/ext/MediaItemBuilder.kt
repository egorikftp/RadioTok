@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.radiotok.radioplayer.ext

import android.net.Uri
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat

fun createPlayableMediaItem(metadata: MediaMetadataCompat): MediaItem {
    return MediaItem(metadata.description, MediaItem.FLAG_PLAYABLE)
}

fun createPlayableMediaItem(
    id: String,
    title: String,
    subtitle: String = "",
    icon: Uri? = null
): MediaItem {
    val mediaDescription = MediaDescriptionCompat.Builder()
        .setMediaId(id)
        .setTitle(title)
        .appendSubtitle(subtitle)
        .appendIcon(icon)
        .build()

    return MediaItem(
        mediaDescription,
        MediaItem.FLAG_PLAYABLE
    )
}

inline fun MediaDescriptionCompat.Builder.appendSubtitle(subtitle: String): MediaDescriptionCompat.Builder {
    if (subtitle.isNotEmpty()) {
        setSubtitle(subtitle)
    }

    return this
}

inline fun MediaDescriptionCompat.Builder.appendIcon(icon: Uri?): MediaDescriptionCompat.Builder {
    if (icon != null) {
        setIconUri(icon)
    }

    return this
}

