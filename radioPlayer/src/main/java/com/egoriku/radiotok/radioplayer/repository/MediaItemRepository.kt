package com.egoriku.radiotok.radioplayer.repository

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
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
    private val context: Context,
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
                title = stringResource.random,
                bitmap = bitmapProvider.icShuffleRounded
            )
        )

        runBlocking {
            if (radioTokDb.stationDao().likedStationsCount() > 0) {
                add(
                    createPlayableMediaItem(
                        id = ShuffleAndPlay.ShuffleLiked.path,
                        title = stringResource.liked,
                        bitmap = bitmapProvider.icLikedRounded
                    )
                )
            }
        }
    }

    override fun getPersonalPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = PersonalPlaylists.Random.path,
            title = "Random",
            bitmap = bitmapProvider.icShuffleRounded
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylists.Liked.path,
            title = "Liked",
            bitmap = bitmapProvider.icLikedRounded
        ),
        createBrowsableMediaItem(
            id = PersonalPlaylists.RecentlyPlayed.path,
            title = "Recently Played"
        )
    )

    override fun getSmartPlaylistsItems() = listOf(
        createBrowsableMediaItem(
            id = SmartPlaylists.LocalStations.path,
            title = "Local Stations"
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.TopClicks.path,
            title = "Top Clicks"
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.TopVote.path,
            title = "TopVote"
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.ChangedLately.path,
            title = "Changed Lately"
        ),
        createBrowsableMediaItem(
            id = SmartPlaylists.Playing.path,
            title = "Playing"
        )
    )

    override fun getCatalogItems() = listOf(
        createBrowsableMediaItem(
            id = Catalog.ByGenres.path,
            title = "By Genres"
        ),
        createBrowsableMediaItem(
            id = Catalog.ByCountry.path,
            title = "By Country"
        ),
        createBrowsableMediaItem(
            id = Catalog.ByLanguage.path,
            title = "By Language"
        )
    )

    override suspend fun getRandomItem(): MediaMetadataCompat {
        val randomStation = radioTokDb.stationDao().getRandomStation()

        return MediaMetadataCompat.Builder().from(
            itemModel = mapper.invoke(randomStation),
            context = context
        ).build()
    }

    override suspend fun getLikedItem(): MediaMetadataCompat {
        val randomStation = radioTokDb.stationDao().getRandomLikedStation()

        return MediaMetadataCompat.Builder().from(
            itemModel = mapper.invoke(randomStation),
            context = context
        ).build()
    }
}