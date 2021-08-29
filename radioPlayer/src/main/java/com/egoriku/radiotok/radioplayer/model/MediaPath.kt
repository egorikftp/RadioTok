package com.egoriku.radiotok.radioplayer.model

import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_PERSONAL_COLLECTION
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SHUFFLE_AND_PLAY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SMART_CATALOG
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SMART_COLLECTION
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_COUNTRY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_LANGUAGE
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_TAGS
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_CHANGED_LATELY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_DISLIKED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_LIKED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_LOCAL_STATIONS
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_PLAYING
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_RECENTLY_PLAYED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_SHUFFLE_LIKED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_SHUFFLE_RANDOM
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_TOP_CLICKS
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_TOP_VOTE

sealed class MediaPath(val path: String, val isPlayable: Boolean = false) {

    object Root : MediaPath(path = PATH_ROOT, isPlayable = false)

    object ShuffleAndPlayRoot : MediaPath(path = PATH_ROOT_SHUFFLE_AND_PLAY) {
        object ShuffleRandom : MediaPath(path = SUB_PATH_SHUFFLE_RANDOM, isPlayable = true)
        object ShuffleLiked : MediaPath(path = SUB_PATH_SHUFFLE_LIKED, isPlayable = true)
    }

    object PersonalPlaylistsRoot : MediaPath(path = PATH_ROOT_PERSONAL_COLLECTION) {
        object Liked : MediaPath(path = SUB_PATH_LIKED)
        object RecentlyPlayed : MediaPath(path = SUB_PATH_RECENTLY_PLAYED)
        object Disliked : MediaPath(path = SUB_PATH_DISLIKED)
    }

    object SmartPlaylistsRoot : MediaPath(path = PATH_ROOT_SMART_COLLECTION) {
        object LocalStations : MediaPath(path = SUB_PATH_LOCAL_STATIONS)
        object TopClicks : MediaPath(path = SUB_PATH_TOP_CLICKS)
        object TopVote : MediaPath(path = SUB_PATH_TOP_VOTE)
        object ChangedLately : MediaPath(path = SUB_PATH_CHANGED_LATELY)
        object Playing : MediaPath(path = SUB_PATH_PLAYING)
    }

    object CatalogRoot : MediaPath(path = PATH_ROOT_SMART_CATALOG) {
        object ByTags : MediaPath(path = SUB_PATH_BY_TAGS)
        object ByCountry : MediaPath(path = SUB_PATH_BY_COUNTRY)
        object ByLanguage : MediaPath(path = SUB_PATH_BY_LANGUAGE)
    }

    companion object {

        private val availableMedia by lazy {
            listOf(
                Root,
                ShuffleAndPlayRoot,
                ShuffleAndPlayRoot.ShuffleRandom,
                ShuffleAndPlayRoot.ShuffleLiked,
                PersonalPlaylistsRoot,
                PersonalPlaylistsRoot.Disliked,
                PersonalPlaylistsRoot.Liked,
                PersonalPlaylistsRoot.RecentlyPlayed,
                SmartPlaylistsRoot,
                SmartPlaylistsRoot.LocalStations,
                SmartPlaylistsRoot.TopClicks,
                SmartPlaylistsRoot.TopVote,
                SmartPlaylistsRoot.ChangedLately,
                SmartPlaylistsRoot.Playing,
                CatalogRoot,
                CatalogRoot.ByTags,
                CatalogRoot.ByCountry,
                CatalogRoot.ByLanguage
            )
        }

        fun fromParentIdOrThrow(id: String) = find(id)
            ?: throw IllegalArgumentException("Unknown id = $id")

        fun fromParentIdOrNull(id: String) = find(id)

        private fun find(id: String) = availableMedia.find { id == it.path }
    }
}