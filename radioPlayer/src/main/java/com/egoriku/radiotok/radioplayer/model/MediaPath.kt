package com.egoriku.radiotok.radioplayer.model

import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_LIKED_RADIO
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_RANDOM_RADIO
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_ROOT

sealed class MediaPath(val path: String) {

    object Root : MediaPath(path = MEDIA_PATH_ROOT)
    object RandomRadio : MediaPath(path = MEDIA_PATH_RANDOM_RADIO)
    object LikedRadio : MediaPath(path = MEDIA_PATH_LIKED_RADIO)

    companion object {

        fun fromParentIdOrThrow(id: String) = when (id) {
            Root.path -> Root
            RandomRadio.path -> RandomRadio
            LikedRadio.path -> LikedRadio
            else -> throw IllegalArgumentException("Unknown id = $id")
        }

        fun fromParentIdOrNull(id: String) = when (id) {
            Root.path -> Root
            RandomRadio.path -> RandomRadio
            LikedRadio.path -> LikedRadio
            else -> null
        }
    }
}
