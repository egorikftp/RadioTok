@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.radiotok.common.ext

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

inline fun Context.getIconUri(iconId: Int): Uri = Uri.Builder()
    .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
    .authority(resources.getResourcePackageName(iconId))
    .appendPath(resources.getResourceTypeName(iconId))
    .appendPath(resources.getResourceEntryName(iconId))
    .build()