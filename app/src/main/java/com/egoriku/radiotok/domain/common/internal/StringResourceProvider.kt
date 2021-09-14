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
    override val byTags by ResourceDelegate(R.string.media_item_path_by_tags)
    override val byCountry by ResourceDelegate(R.string.media_item_path_by_country)
    override val byLanguage by ResourceDelegate(R.string.media_item_path_by_language)
    override val catalog by ResourceDelegate(R.string.media_item_path_catalog)

    override val likedRadio by ResourceDelegate(R.string.media_item_path_liked_radio)
    override val randomRadio by ResourceDelegate(R.string.media_item_path_random_radio)

    override val liked: String by ResourceDelegate(R.string.media_item_path_liked)
    override val recentlyPlayed: String by ResourceDelegate(R.string.media_item_path_recently_played)
    override val disliked: String by ResourceDelegate(R.string.media_item_path_disliked)

    override val localStations: String by ResourceDelegate(R.string.media_item_path_local_stations)
    override val topClicks: String by ResourceDelegate(R.string.media_item_path_top_clicks)
    override val topVote: String by ResourceDelegate(R.string.media_item_path_top_vote)
    override val changedLately: String by ResourceDelegate(R.string.media_item_path_changed_lately)
    override val playing: String by ResourceDelegate(R.string.media_item_path_playing)

    override fun getStationsCount(count: Int) =
        context.resources.getQuantityString(R.plurals.radio_station_count, count, count)
}