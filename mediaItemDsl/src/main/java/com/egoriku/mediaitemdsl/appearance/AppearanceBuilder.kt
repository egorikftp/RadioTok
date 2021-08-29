package com.egoriku.mediaitemdsl.appearance

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import androidx.media.utils.MediaConstants.*

@AppearanceMarker
class AppearanceBuilder @PublishedApi internal constructor(
    private val flag: Int,
    private val extras: Bundle
) {
    var showAsList: Boolean = false
        set(value) {
            field = value

            if (value) {
                when (flag) {
                    MediaItem.FLAG_BROWSABLE -> extras.putInt(
                        DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE,
                        DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM
                    )
                    MediaItem.FLAG_PLAYABLE -> extras.putInt(
                        DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE,
                        DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_LIST_ITEM
                    )
                }
            }
        }

    var showAsGrid: Boolean = false
        set(value) {
            field = value

            if (value) {
                when (flag) {
                    MediaItem.FLAG_BROWSABLE -> extras.putInt(
                        DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_BROWSABLE,
                        DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_GRID_ITEM
                    )
                    MediaItem.FLAG_PLAYABLE -> extras.putInt(
                        DESCRIPTION_EXTRAS_KEY_CONTENT_STYLE_PLAYABLE,
                        DESCRIPTION_EXTRAS_VALUE_CONTENT_STYLE_GRID_ITEM
                    )
                }
            }
        }
}