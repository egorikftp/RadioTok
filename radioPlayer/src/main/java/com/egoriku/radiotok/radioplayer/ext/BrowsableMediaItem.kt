package com.egoriku.radiotok.radioplayer.ext

import android.graphics.Bitmap
import android.net.Uri
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat

fun createBrowsableMediaItem(
    id: String,
    title: String,
    subtitle: String = "",
    icon: Uri? = null,
    bitmap: Bitmap? = null,
    showAsList: Boolean = false
): MediaBrowserCompat.MediaItem {
    val mediaDescriptionBuilder = MediaDescriptionCompat.Builder()
        .appendExtras(showAsList)
        .setMediaId(id)
        .setTitle(title)

    if (subtitle.isNotEmpty()) {
        mediaDescriptionBuilder.setSubtitle(subtitle)
    }
    if (icon != null) {
        mediaDescriptionBuilder.setIconUri(icon)
    }
    if (bitmap != null) {
        mediaDescriptionBuilder.setIconBitmap(bitmap)
    }

    return MediaBrowserCompat.MediaItem(
        mediaDescriptionBuilder.build(),
        MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
    )
}
