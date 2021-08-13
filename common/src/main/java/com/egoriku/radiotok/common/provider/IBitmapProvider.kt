package com.egoriku.radiotok.common.provider

import android.graphics.Bitmap

interface IBitmapProvider {

    val icCollection: Bitmap
    val icLikedRounded: Bitmap
    val icPersonal: Bitmap
    val icRadioWaves: Bitmap
    val icSmartPlaylist: Bitmap

    val icShuffleRounded: Bitmap
}