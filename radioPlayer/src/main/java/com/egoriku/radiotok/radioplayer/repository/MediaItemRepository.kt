package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.egoriku.radiotok.common.provider.IBitmapProvider
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.db.mapper.DbStationToModelMapper
import com.egoriku.radiotok.radioplayer.ext.createBrowsableMediaItem
import com.egoriku.radiotok.radioplayer.ext.createPlayableMediaItem
import com.egoriku.radiotok.radioplayer.ext.from
import com.egoriku.radiotok.radioplayer.model.MediaPath.*
import kotlinx.coroutines.runBlocking

internal class MediaItemRepository(
    private val bitmapProvider: IBitmapProvider,
    private val stringResource: IStringResourceProvider,
    private val radioTokDb: RadioTokDb
) : IMediaItemRepository {

    private val mapper = DbStationToModelMapper()

    override fun getRootItems() = listOf(
        createBrowsableMediaItem(
            id = ShuffleAndPlay.path,
            title = stringResource.shuffleAndPlay,
            bitmap = bitmapProvider.icRadioWaves
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylists.path,
            title = stringResource.personalPlaylists,
            bitmap = bitmapProvider.icPersonal
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.path,
            title = stringResource.smartPlaylists,
            bitmap = bitmapProvider.icSmartPlaylist
        ),
        createBrowsableMediaItem(
            id = Catalog.path,
            title = stringResource.catalog,
            bitmap = bitmapProvider.icCollection
        )
    )

    @OptIn(ExperimentalStdlibApi::class)
    override fun getShuffleAndPlayItems() = buildList {
        add(
            createPlayableMediaItem(
                id = ShuffleAndPlay.ShuffleRandom.path,
                title = stringResource.randomRadio,
                bitmap = bitmapProvider.icShuffleRound
            )
        )

        runBlocking {
            val mediaItem = if (radioTokDb.stationDao().likedStationsCount() > 0) {
                createPlayableMediaItem(
                    id = ShuffleAndPlay.ShuffleLiked.path,
                    title = stringResource.likedRadio,
                    bitmap = bitmapProvider.icLikedRound
                )
            } else {
                createBrowsableMediaItem(
                    id = ShuffleAndPlay.ShuffleLiked.path,
                    title = stringResource.likedRadio,
                    bitmap = bitmapProvider.icLikedRound
                )
            }

            add(mediaItem)
        }
    }

    override fun getPersonalPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = PersonalPlaylists.Liked.path,
            title = stringResource.liked,
            bitmap = bitmapProvider.icLikedRound
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylists.RecentlyPlayed.path,
            title = stringResource.recentlyPlayed,
            bitmap = bitmapProvider.icHistoryRound
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylists.Disliked.path,
            title = stringResource.disliked,
            bitmap = bitmapProvider.icDislikedRound
        )
    )

    override fun getLikedItems(): List<MediaBrowserCompat.MediaItem> {
        return runBlocking {
            radioTokDb.stationDao().getLikedStations().map {
                createPlayableMediaItem(
                    showAsList = true,
                    id = it.stationUuid,
                    title = it.name,
                    subtitle = it.tags,
                    icon = it.icon.toUri()
                )
            }
        }
    }

    override fun getRecentlyPlayedItems(): List<MediaBrowserCompat.MediaItem> {
        return emptyList()
    }

    override fun getSmartPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = SmartPlaylists.LocalStations.path,
            title = stringResource.localStations,
            bitmap = bitmapProvider.icLocalRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.TopClicks.path,
            title = stringResource.topClicks,
            bitmap = bitmapProvider.icTopClicksRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.TopVote.path,
            title = stringResource.topVote,
            bitmap = bitmapProvider.icTopVoteRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.ChangedLately.path,
            title = stringResource.changedLately,
            bitmap = bitmapProvider.icChangedLatelyRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.Playing.path,
            title = stringResource.playing,
            bitmap = bitmapProvider.icPlayingRound
        )
    )

    override fun getCatalogItems() = listOf(
        createBrowsableMediaItem(
            id = Catalog.ByGenres.path,
            title = stringResource.byGenres,
            bitmap = bitmapProvider.icGenresRound
        ),
        createBrowsableMediaItem(
            id = Catalog.ByCountry.path,
            title = stringResource.byCountry,
            bitmap = bitmapProvider.icCountryRounded
        ),
        createBrowsableMediaItem(
            id = Catalog.ByLanguage.path,
            title = stringResource.byLanguage,
            bitmap = bitmapProvider.icLanguageRound
        )
    )

    override suspend fun getRandomItem(): MediaMetadataCompat {
        val randomStation = radioTokDb.stationDao().getRandomStation()

        return MediaMetadataCompat.Builder().from(
            itemModel = mapper.invoke(randomStation)
        ).build()
    }

    override suspend fun getLikedItem(): MediaMetadataCompat {
        val randomStation = radioTokDb.stationDao().getRandomLikedStation()

        return MediaMetadataCompat.Builder().from(
            itemModel = mapper.invoke(randomStation)
        ).build()
    }
}