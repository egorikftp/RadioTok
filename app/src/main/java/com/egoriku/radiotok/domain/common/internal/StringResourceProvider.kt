package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.provider.IStringResourceProvider

internal class StringResourceProvider(
    private val context: Context
) : IStringResourceProvider {

    override val collection: String
        get() = context.getString(R.string.media_item_path_collection)

    override val forYou: String
        get() = context.getString(R.string.media_item_path_for_you)

    override val likedRadio: String
        get() = context.getString(R.string.media_item_path_liked_radio)

    override val randomRadio: String
        get() = context.getString(R.string.media_item_path_random_radio)
}