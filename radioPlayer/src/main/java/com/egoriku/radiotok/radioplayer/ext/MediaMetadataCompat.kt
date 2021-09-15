package com.egoriku.radiotok.radioplayer.ext

import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.egoriku.radiotok.common.entity.RadioEntity
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.common.mapper.MetadataBuilder
import com.egoriku.radiotok.common.model.RadioItemModel

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

fun MediaMetadataCompat.Builder.from(itemModel: RadioItemModel): MediaMetadataCompat.Builder {

    logD("_____")
    logD("name: ${itemModel.name}")
    logD("icon: ${itemModel.icon}")
    logD("id: ${itemModel.id}")
    logD("_____")

    id = itemModel.id
    displayTitle = itemModel.name
    displaySubtitle = itemModel.metadata
    displayIconUri = itemModel.icon
    mediaUri = itemModel.streamUrl

    isHls = itemModel.hls

    return this
}

fun MediaMetadataCompat.Builder.from(entity: RadioEntity): MediaMetadataCompat.Builder {

    logD("_____")
    logD("name: ${entity.name}")
    logD("icon: ${entity.icon}")
    logD("id: ${entity.stationUuid}")
    logD("_____")

    id = entity.stationUuid
    displayTitle = entity.name
    displaySubtitle = MetadataBuilder.build(
        countryCode = entity.countryCode,
        tags = entity.tags
    )
    displayIconUri = entity.icon
    mediaUri = entity.streamUrl

    isHls = entity.hls

    return this
}