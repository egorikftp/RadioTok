package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.ext.drawableCompat
import com.egoriku.radiotok.common.provider.IBitmapProvider
import com.egoriku.radiotok.extension.roundWithBorder

internal class BitmapProvider(private val context: Context) : IBitmapProvider {

    override val icCollection: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_collection).toBitmap()

    override val icPersonal: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_personal).toBitmap()

    override val icRadioWaves: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_radio_waves).toBitmap()

    override val icSmartPlaylist: Bitmap
        get() = context.drawableCompat(R.drawable.ic_auto_smart_playlist).toBitmap()

    override val icDislikedRounded: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_not_interested)
            .toBitmap()
            .roundWithBorder()

    override val icHistoryRounded: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_history)
            .toBitmap()
            .roundWithBorder()

    override val icLikedRounded: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_liked)
            .toBitmap()
            .roundWithBorder()

    override val icShuffleRounded: Bitmap
        get() = context
            .drawableCompat(R.drawable.ic_auto_shuffle)
            .toBitmap()
            .roundWithBorder()
}