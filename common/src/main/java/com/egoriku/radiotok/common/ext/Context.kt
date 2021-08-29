package com.egoriku.radiotok.common.ext

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

fun Context.drawableCompat(@DrawableRes resId: Int) = requireNotNull(
    AppCompatResources.getDrawable(this, resId)
)
