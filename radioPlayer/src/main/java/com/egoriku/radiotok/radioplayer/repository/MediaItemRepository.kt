package com.egoriku.radiotok.radioplayer.repository

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.provider.IDrawableProvider
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
    private val drawableProvider: IDrawableProvider,
    private val stringResource: IStringResourceProvider,
    private val radioTokDb: RadioTokDb
) : IMediaItemRepository {

    private val mapper = DbStationToModelMapper()

    override fun getRootItems(): List<MediaBrowserCompat.MediaItem> = listOf(
        createBrowsableMediaItem(
            id = RootForYou.path,
            title = stringResource.forYou,
            bitmap = drawableProvider.icRadioWave
        ),
        createBrowsableMediaItem(
            id = RootCollection.path,
            title = stringResource.collection,
            bitmap = drawableProvider.icCollection
        )
    )

    @OptIn(ExperimentalStdlibApi::class)
    override fun getForYouItems(): List<MediaBrowserCompat.MediaItem> {
        return buildList {
            add(
                createPlayableMediaItem(
                    id = RandomRadio.path,
                    title = stringResource.randomRadio,
                    icon = drawableProvider.icRandom
                )
            )

            runBlocking {
                if (radioTokDb.stationDao().likedStationsCount() > 0) {
                    add(
                        createPlayableMediaItem(
                            id = LikedRadio.path,
                            title = stringResource.likedRadio,
                            icon = drawableProvider.icHeart
                        )
                    )
                }
            }
        }
    }

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