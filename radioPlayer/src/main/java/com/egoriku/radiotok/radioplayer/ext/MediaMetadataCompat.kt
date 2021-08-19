package com.egoriku.radiotok.radioplayer.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.radioplayer.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val NO_GET = "Property does not have a 'get'"

const val METADATA_KEY_IS_HLS = "METADATA_KEY_IS_HLS"

inline val MediaMetadataCompat.mediaUri: Uri
    get() = getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI).toUri()

inline val MediaMetadataCompat.id: String
    get() = getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)

inline val MediaMetadataCompat.isHls: Boolean
    get() = getLong(METADATA_KEY_IS_HLS) == 1L

inline var MediaMetadataCompat.Builder.id: String
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, value)
    }

inline var MediaMetadataCompat.Builder.mediaUri: String?
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, value)
    }

inline var MediaMetadataCompat.Builder.displaySubtitle: String?
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, value)
    }

inline var MediaMetadataCompat.Builder.displayTitle: String?
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, value)
    }

inline var MediaMetadataCompat.Builder.displayIconBitmap: Bitmap
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, value)
    }


inline var MediaMetadataCompat.Builder.displayIconUri: String?
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, value)
    }

inline var MediaMetadataCompat.Builder.isHls: Long
    @Deprecated(NO_GET, level = DeprecationLevel.ERROR)
    get() = throw IllegalAccessException("Cannot get from MediaMetadataCompat.Builder")
    set(value) {
        putLong(METADATA_KEY_IS_HLS, value)
    }

suspend fun MediaMetadataCompat.Builder.from(
    itemModel: RadioItemModel,
    context: Context
): MediaMetadataCompat.Builder {

    logD(itemModel.name)
    logD(itemModel.icon)

    id = itemModel.id
    displayTitle = itemModel.name
    displaySubtitle = itemModel.metadata
    val loadBitmap = loadBitmap(context, itemModel.icon)

    displayIconBitmap = loadBitmap
    displayIconUri = itemModel.icon

    mediaUri = itemModel.streamUrl

    isHls = itemModel.hls

    return this
}

private suspend fun loadBitmap(
    context: Context,
    iconUri: String
): Bitmap = withContext(Dispatchers.IO) {
    runCatching {
        if (iconUri.isEmpty()) {
            throw IllegalArgumentException()
        }

        Glide.with(context)
            .asBitmap()
            .load(iconUri)
            .submit()
            .get()
    }.getOrDefault(
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_radio_round)
    )
}
