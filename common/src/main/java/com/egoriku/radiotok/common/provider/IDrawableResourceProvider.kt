package com.egoriku.radiotok.common.provider

import android.net.Uri

interface IDrawableResourceProvider {

    val iconAllRadioStations: Int
    val iconAllRadioUri: Uri

    val iconLikedRadioStations: Int
    val iconLikedRadioUri: Uri
}