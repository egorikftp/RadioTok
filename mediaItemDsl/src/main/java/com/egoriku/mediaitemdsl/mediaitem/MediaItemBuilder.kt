package com.egoriku.mediaitemdsl.mediaitem

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import androidx.annotation.RestrictTo
import androidx.core.os.bundleOf
import com.egoriku.mediaitemdsl.appearance.AppearanceBuilder

@MediaItemMarker
class MediaItemBuilder @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP) @PublishedApi internal constructor(
    private val flag: Int,
    @PublishedApi internal val mediaDescriptionBuilder: MediaDescriptionCompat.Builder
) {

    @PublishedApi
    internal constructor(flag: Int) : this(
        flag = flag,
        mediaDescriptionBuilder = MediaDescriptionCompat.Builder()
    )

    private val appearance: AppearanceBuilder
        get() = AppearanceBuilder(flag = flag, extras = extras)

    fun appearance(body: @MediaItemMarker AppearanceBuilder.() -> Unit) {
        appearance.body()
    }

    var extras: Bundle = bundleOf()
        set(value) {
            field.putAll(value)
        }

    var iconBitmap: Bitmap? = null
        set(value) {
            field = value
            mediaDescriptionBuilder.setIconBitmap(value)
        }

    var iconUri: Uri? = null
        set(value) {
            field = value
            mediaDescriptionBuilder.setIconUri(value)
        }

    var id: String? = null
        set(value) {
            field = value
            mediaDescriptionBuilder.setMediaId(value)
        }

    var title: String? = null
        set(value) {
            field = value
            mediaDescriptionBuilder.setTitle(value)
        }

    var subTitle: String? = null
        set(value) {
            field = value
            mediaDescriptionBuilder.setSubtitle(value)
        }

    fun build() = MediaBrowserCompat.MediaItem(
        mediaDescriptionBuilder
            .setExtras(extras)
            .build(),
        flag
    )
}
