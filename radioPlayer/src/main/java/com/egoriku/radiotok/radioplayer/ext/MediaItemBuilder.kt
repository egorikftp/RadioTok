@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.radiotok.radioplayer.ext

import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.os.bundleOf
import androidx.media.utils.MediaConstants.DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE
import androidx.media.utils.MediaConstants.DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM

fun createPlayableMediaItem(metadata: MediaMetadataCompat): MediaItem {
    return MediaItem(metadata.description, MediaItem.FLAG_PLAYABLE)
}

fun createPlayableMediaItem(
    id: String,
    title: String,
    subtitle: String = "",
    icon: Uri? = null,
    bitmap: Bitmap? = null,
    showAsList: Boolean = false
): MediaItem {
    val mediaDescription = MediaDescriptionCompat.Builder()
        .setMediaId(id)
        .setTitle(title)
        .appendSubtitle(subtitle)
        .appendIcon(icon)
        .appendBitmap(bitmap)
        .appendExtras(showAsList)
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

inline fun MediaDescriptionCompat.Builder.appendBitmap(bitmap: Bitmap?): MediaDescriptionCompat.Builder {
    if (bitmap != null) {
        setIconBitmap(bitmap)
    }

    return this
}

inline fun MediaDescriptionCompat.Builder.appendExtras(isListItems: Boolean): MediaDescriptionCompat.Builder {
    if (isListItems) {
        val bundle = bundleOf(
            DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE to DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM,
        )

        setExtras(bundle)
    }

    return this
}