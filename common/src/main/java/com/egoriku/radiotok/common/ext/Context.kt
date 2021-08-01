package com.egoriku.radiotok.common.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

fun Context.drawableCompat(@DrawableRes resId: Int) = requireNotNull(
    AppCompatResources.getDrawable(this, resId)
)

inline fun Context.drawableCompatWithTint(
    @DrawableRes resId: Int,
    @ColorRes tint: Int
): Drawable = drawableCompat(resId).apply {
    mutate()
    when (tint) {
        0 -> setTintList(null)
        else -> setTint(colorCompat(tint))
    }
}

fun Context.colorCompat(@ColorRes colorResId: Int) = ContextCompat.getColor(this, colorResId)
