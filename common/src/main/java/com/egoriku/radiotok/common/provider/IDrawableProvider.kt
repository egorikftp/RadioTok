package com.egoriku.radiotok.common.provider

import android.graphics.Bitmap
import android.net.Uri

interface IDrawableProvider {

    val icCollection: Bitmap
    val icRadioWave: Bitmap

    val icHeart: Uri
    val icRandom: Uri
}