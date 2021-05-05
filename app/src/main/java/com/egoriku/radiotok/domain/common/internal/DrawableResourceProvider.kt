package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import android.net.Uri
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.ext.getIconUri
import com.egoriku.radiotok.common.provider.IDrawableResourceProvider

internal class DrawableResourceProvider(
    private val context: Context
) : IDrawableResourceProvider {

    override val iconAllRadioStations: Int
        get() = R.drawable.ic_radio_tower

    override val iconAllRadioUri: Uri
        get() = context.getIconUri(iconId = iconAllRadioStations)

    override val iconLikedRadioStations: Int
        get() = R.drawable.ic_favorite

    override val iconLikedRadioUri: Uri
        get() = context.getIconUri(iconId = iconLikedRadioStations)
}