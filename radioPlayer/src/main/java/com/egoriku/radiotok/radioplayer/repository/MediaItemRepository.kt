package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import androidx.core.net.toUri
import com.egoriku.radiotok.common.datasource.ITagsDataSource
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
    private val radioTokDb: RadioTokDb,
    private val tagsDataSource: ITagsDataSource
) : IMediaItemRepository {

    private val mapper = DbStationToModelMapper()

    override fun getRootItems() = listOf(
        createBrowsableMediaItem(
            id = ShuffleAndPlayRoot.path,
            title = stringResource.shuffleAndPlay,
            bitmap = bitmapProvider.icRadioWaves
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylistsRoot.path,
            title = stringResource.personalPlaylists,
            bitmap = bitmapProvider.icPersonal
        ),
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.path,
            title = stringResource.smartPlaylists,
            bitmap = bitmapProvider.icSmartPlaylist
        ),
        createBrowsableMediaItem(
            id = CatalogRoot.path,
            title = stringResource.catalog,
            bitmap = bitmapProvider.icCollection
        )
    )

    @OptIn(ExperimentalStdlibApi::class)
    override fun getShuffleAndPlayItems() = buildList {
        add(
            createPlayableMediaItem(
                id = ShuffleAndPlayRoot.ShuffleRandom.path,
                title = stringResource.randomRadio,
                bitmap = bitmapProvider.icShuffleRound
            )
        )

        runBlocking {
            val mediaItem = if (radioTokDb.stationDao().likedStationsCount() > 0) {
                createPlayableMediaItem(
                    id = ShuffleAndPlayRoot.ShuffleLiked.path,
                    title = stringResource.likedRadio,
                    bitmap = bitmapProvider.icLikedRound
                )
            } else {
                createBrowsableMediaItem(
                    id = ShuffleAndPlayRoot.ShuffleLiked.path,
                    title = stringResource.likedRadio,
                    bitmap = bitmapProvider.icLikedRound
                )
            }

            add(mediaItem)
        }
    }

    override fun getPersonalPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = PersonalPlaylistsRoot.Liked.path,
            title = stringResource.liked,
            bitmap = bitmapProvider.icLikedRound
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylistsRoot.RecentlyPlayed.path,
            title = stringResource.recentlyPlayed,
            bitmap = bitmapProvider.icHistoryRound
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylistsRoot.Disliked.path,
            title = stringResource.disliked,
            bitmap = bitmapProvider.icDislikedRound
        )
    )

    override fun getLikedItems() = runBlocking {
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

    override fun getRecentlyPlayedItems(): List<MediaBrowserCompat.MediaItem> {
        return emptyList()
    }

    override fun getSmartPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.LocalStations.path,
            title = stringResource.localStations,
            bitmap = bitmapProvider.icLocalRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.TopClicks.path,
            title = stringResource.topClicks,
            bitmap = bitmapProvider.icTopClicksRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.TopVote.path,
            title = stringResource.topVote,
            bitmap = bitmapProvider.icTopVoteRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.ChangedLately.path,
            title = stringResource.changedLately,
            bitmap = bitmapProvider.icChangedLatelyRound
        ),
        createBrowsableMediaItem(
            id = SmartPlaylistsRoot.Playing.path,
            title = stringResource.playing,
            bitmap = bitmapProvider.icPlayingRound
        )
    )

    override fun getCatalogItems() = listOf(
        createBrowsableMediaItem(
            id = CatalogRoot.ByTags.path,
            title = stringResource.byTags,
            bitmap = bitmapProvider.icTagsRound
        ),
        createBrowsableMediaItem(
            id = CatalogRoot.ByCountry.path,
            title = stringResource.byCountry,
            bitmap = bitmapProvider.icCountryRounded
        ),
        createBrowsableMediaItem(
            id = CatalogRoot.ByLanguage.path,
            title = stringResource.byLanguage,
            bitmap = bitmapProvider.icLanguageRound
        )
    )

    override fun getCatalogTags() = runBlocking {
        val groupedTags = tagsDataSource.getGroupedTags()

        groupedTags.map {
            createBrowsableMediaItem(
                showAsList = true,
                id = it.key,
                title = it.key,
                subtitle = "${it.value.size} stations"
            )
        }
    }

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