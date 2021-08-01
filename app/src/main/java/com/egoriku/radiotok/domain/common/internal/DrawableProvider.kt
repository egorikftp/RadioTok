package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.ext.drawableCompatWithTint
import com.egoriku.radiotok.common.ext.getIconUri
import com.egoriku.radiotok.common.provider.IDrawableProvider

internal class DrawableProvider(private val context: Context) : IDrawableProvider {

    override val icCollection: Bitmap
        get() = context.drawableCompatWithTint(
            resId = R.drawable.ic_collection,
            tint = R.color.white
        ).toBitmap()

    override val icRadioWave: Bitmap
        get() = context.drawableCompatWithTint(
            resId = R.drawable.ic_radio_waves,
            tint = R.color.white
        ).toBitmap()

    override val icHeart: Uri
        get() = context.getIconUri(R.drawable.ic_heart)

    override val icRandom: Uri
        get() = context.getIconUri(R.drawable.ic_random)
}