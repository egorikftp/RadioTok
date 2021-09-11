package com.egoriku.radiotok.domain.usecase

import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.datasource.ICountriesDataSource
import com.egoriku.radiotok.common.datasource.ILanguagesDataSource
import com.egoriku.radiotok.common.datasource.ITagsDataSource
import com.egoriku.radiotok.common.ext.toFlagEmoji
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.domain.model.Feed
import com.egoriku.radiotok.domain.model.section.FeedType
import com.egoriku.radiotok.domain.model.section.FeedType.Playlist
import com.egoriku.radiotok.domain.model.section.FeedType.SimplePlaylist
import com.egoriku.radiotok.radioplayer.constant.MediaBrowserConstant
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FeedUseCase(
    private val tagsDataSource: ITagsDataSource,
    private val languagesDataSource: ILanguagesDataSource,
    private val countriesDataSource: ICountriesDataSource,
    private val stringResource: IStringResourceProvider
) {

    suspend fun loadFeed(): Feed = coroutineScope {

        val shuffleAndPlay = getShuffleAndPlay()
        val forYou = getForYou()
        val smartPlaylists = getSmartPlaylists()

        val tagsDeferred = async {
            tagsDataSource.load().map {
                SimplePlaylist(
                    name = it.name,
                    count = it.count.toString()
                )
            }
        }

        val languagesDeferred = async {
            languagesDataSource.load().map {
                SimplePlaylist(
                    name = it.name,
                    count = it.count.toString()
                )
            }
        }

        val countryCodesDeferred = async {
            countriesDataSource.load().map {
                SimplePlaylist(
                    name = it.name.toFlagEmoji,
                    count = it.count.toString()
                )
            }
        }

        Feed(
            shuffleAndPlay = shuffleAndPlay,
            forYou = forYou,
            smartPlaylists = smartPlaylists,
            byTags = tagsDeferred.await(),
            byCountry = countryCodesDeferred.await(),
            byLanguage = languagesDeferred.await()
        )
    }

    private fun getSmartPlaylists() = listOf(
        Playlist(name = stringResource.localStations, icon = R.drawable.ic_local),
        Playlist(name = stringResource.topClicks, icon = R.drawable.ic_top_clicks),
        Playlist(name = stringResource.topVote, icon = R.drawable.ic_top_vote),
        Playlist(name = stringResource.changedLately, icon = R.drawable.ic_changed_lately),
        Playlist(name = stringResource.playing, icon = R.drawable.ic_playing)
    )

    private fun getForYou() = listOf(
        Playlist(name = stringResource.liked, icon = R.drawable.ic_favorite),
        Playlist(name = stringResource.recentlyPlayed, icon = R.drawable.ic_history),
        Playlist(name = stringResource.disliked, icon = R.drawable.ic_not_interested)
    )

    private fun getShuffleAndPlay() = listOf(
        FeedType.InstantPlay(
            mediaId = MediaBrowserConstant.SUB_PATH_SHUFFLE_RANDOM,
            name = stringResource.randomRadio,
            icon = R.drawable.ic_random
        ),
        FeedType.InstantPlay(
            mediaId = MediaBrowserConstant.SUB_PATH_SHUFFLE_LIKED,
            name = stringResource.likedRadio,
            icon = R.drawable.ic_favorite
        )
    )
}