package com.egoriku.radiotok.domain.model

import com.egoriku.radiotok.R
import com.egoriku.radiotok.domain.model.section.FeedType

data class Feed(
    val shuffleAndPlay: Lane.ShuffleAndPlay,
    val forYou: Lane.ForYou,
    val smartPlaylists: Lane.SmartPlaylist,
    val byTags: Lane.ByTag,
    val byCountry: Lane.ByCountry,
    val byLanguage: Lane.ByLanguage
)

sealed class Lane {
    abstract val titleRes: Int
    abstract val showMore: Boolean
    abstract val items: List<FeedType>

    data class ShuffleAndPlay(
        override val titleRes: Int = R.string.media_item_path_shuffle_and_play,
        override val showMore: Boolean = false,
        override val items: List<FeedType.InstantPlay>
    ) : Lane()

    data class ForYou(
        override val titleRes: Int = R.string.media_item_path_personal_playlists,
        override val showMore: Boolean = false,
        override val items: List<FeedType.Playlist>
    ) : Lane()

    data class SmartPlaylist(
        override val titleRes: Int = R.string.media_item_path_smart_playlists,
        override val showMore: Boolean = false,
        override val items: List<FeedType.Playlist>
    ) : Lane()

    data class ByTag(
        override val titleRes: Int = R.string.media_item_path_by_tags,
        override val showMore: Boolean = true,
        override val items: List<FeedType.SimplePlaylist>
    ) : Lane()

    data class ByCountry(
        override val titleRes: Int = R.string.media_item_path_by_country,
        override val showMore: Boolean = true,
        override val items: List<FeedType.SimplePlaylist>
    ): Lane()

    data class ByLanguage(
        override val titleRes: Int = R.string.media_item_path_by_language,
        override val showMore: Boolean = true,
        override val items: List<FeedType.SimplePlaylist>
    ): Lane()
}