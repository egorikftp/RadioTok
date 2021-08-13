package com.egoriku.radiotok.radioplayer.model

import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_PERSONAL_COLLECTION
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SHUFFLE_AND_PLAY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SMART_CATALOG
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.PATH_ROOT_SMART_COLLECTION
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_COUNTRY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_GENRES
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_BY_LANGUAGE
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_CHANGED_LATELY
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_LIKED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_LOCAL_STATIONS
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_PLAYING
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_RANDOM
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_RECENTLY_PLAYED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_SHUFFLE_LIKED
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_SHUFFLE_RANDOM
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_TOP_CLICKS
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant.SUB_PATH_TOP_VOTE

sealed class MediaPath(val path: String) {

    object Root : MediaPath(path = PATH_ROOT)

    object ShuffleAndPlay : MediaPath(path = PATH_ROOT_SHUFFLE_AND_PLAY) {
        object ShuffleRandom : MediaPath(path = SUB_PATH_SHUFFLE_RANDOM)
        object ShuffleLiked : MediaPath(path = SUB_PATH_SHUFFLE_LIKED)
    }

    object PersonalPlaylists : MediaPath(path = PATH_ROOT_PERSONAL_COLLECTION) {
        object Random : MediaPath(path = SUB_PATH_RANDOM)
        object Liked : MediaPath(path = SUB_PATH_LIKED)
        object RecentlyPlayed : MediaPath(path = SUB_PATH_RECENTLY_PLAYED)
    }

    object SmartPlaylists : MediaPath(path = PATH_ROOT_SMART_COLLECTION) {
        object LocalStations : MediaPath(path = SUB_PATH_LOCAL_STATIONS)
        object TopClicks : MediaPath(path = SUB_PATH_TOP_CLICKS)
        object TopVote : MediaPath(path = SUB_PATH_TOP_VOTE)
        object ChangedLately : MediaPath(path = SUB_PATH_CHANGED_LATELY)
        object Playing : MediaPath(path = SUB_PATH_PLAYING)
    }

    object Catalog : MediaPath(path = PATH_ROOT_SMART_CATALOG) {
        object ByGenres : MediaPath(path = SUB_PATH_BY_GENRES)
        object ByCountry : MediaPath(path = SUB_PATH_BY_COUNTRY)
        object ByLanguage : MediaPath(path = SUB_PATH_BY_LANGUAGE)
    }

    companion object {

        private val availableMedia by lazy {
            listOf(
                Root,
                ShuffleAndPlay,
                ShuffleAndPlay.ShuffleRandom,
                ShuffleAndPlay.ShuffleLiked,
                PersonalPlaylists,
                PersonalPlaylists.Random,
                PersonalPlaylists.Liked,
                PersonalPlaylists.RecentlyPlayed,
                SmartPlaylists,
                SmartPlaylists.LocalStations,
                SmartPlaylists.TopClicks,
                SmartPlaylists.TopVote,
                SmartPlaylists.ChangedLately,
                SmartPlaylists.Playing,
                Catalog,
                Catalog.ByGenres,
                Catalog.ByCountry,
                Catalog.ByLanguage
            )
        }

        fun fromParentIdOrThrow(id: String) = find(id)
            ?: throw IllegalArgumentException("Unknown id = $id")

        fun fromParentIdOrNull(id: String) = find(id)

        private fun find(id: String) = availableMedia.find { id == it.path }
    }
}