package com.egoriku.radiotok.domain.common.internal

import android.content.Context
import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class StringResourceProvider(
    private val context: Context
) : IStringResourceProvider {

    class ResourceDelegate(
        private val key: Int
    ) : ReadOnlyProperty<StringResourceProvider, String> {

        override fun getValue(
            thisRef: StringResourceProvider,
            property: KProperty<*>
        ): String = thisRef.context.getString(key)
    }

    override val shuffleAndPlay by ResourceDelegate(R.string.media_item_path_shuffle_and_play)
    override val personalPlaylists by ResourceDelegate(R.string.media_item_path_personal_playlists)
    override val smartPlaylists by ResourceDelegate(R.string.media_item_path_smart_playlists)
    override val byGenres by ResourceDelegate(R.string.media_item_path_by_genres)
    override val byCountry by ResourceDelegate(R.string.media_item_path_by_country)
    override val byLanguage by ResourceDelegate(R.string.media_item_path_by_language)
    override val catalog by ResourceDelegate(R.string.media_item_path_catalog)

    override val liked by ResourceDelegate(R.string.media_item_path_liked_radio)
    override val random by ResourceDelegate(R.string.media_item_path_random_radio)
}