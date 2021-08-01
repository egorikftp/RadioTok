package com.egoriku.radiotok.radioplayer.model

import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_LIKED_RADIO
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_RANDOM_RADIO
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_ROOT
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_ROOT_COLLECTION
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.MEDIA_PATH_ROOT_FOR_YOU

sealed class MediaPath(val path: String) {

    object Root : MediaPath(path = MEDIA_PATH_ROOT)
    object RootForYou : MediaPath(path = MEDIA_PATH_ROOT_FOR_YOU)
    object RootCollection : MediaPath(path = MEDIA_PATH_ROOT_COLLECTION)
    object RandomRadio : MediaPath(path = MEDIA_PATH_RANDOM_RADIO)
    object LikedRadio : MediaPath(path = MEDIA_PATH_LIKED_RADIO)

    companion object {

        fun fromParentIdOrThrow(id: String) = find(id)
            ?: throw IllegalArgumentException("Unknown id = $id")

        fun fromParentIdOrNull(id: String) = find(id)

        private fun find(id: String) = when (id) {
            Root.path -> Root
            RootForYou.path -> RootForYou
            RootCollection.path -> RootCollection
            RandomRadio.path -> RandomRadio
            LikedRadio.path -> LikedRadio
            else -> null
        }
    }
}
