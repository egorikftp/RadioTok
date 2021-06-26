package com.egoriku.radiotok.radioplayer.repository

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.provider.IDrawableResourceProvider
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.db.mapper.DbStationToModelMapper
import com.egoriku.radiotok.radioplayer.ext.createPlayableMediaItem
import com.egoriku.radiotok.radioplayer.ext.from
import com.egoriku.radiotok.radioplayer.model.MediaPath

internal class MediaItemRepository(
    private val context: Context,
    private val drawableResourceProvider: IDrawableResourceProvider,
    private val stringResource: IStringResourceProvider,
    private val radioTokDb: RadioTokDb
) : IMediaItemRepository {

    private val mapper = DbStationToModelMapper()

    override fun getRootItems() = listOf(
        createPlayableMediaItem(
            id = MediaPath.RandomRadio.path,
            title = stringResource.randomRadio,
            icon = drawableResourceProvider.iconAllRadioUri
        ),
        createPlayableMediaItem(
            id = MediaPath.LikedRadio.path,
            title = stringResource.likedRadio,
            icon = drawableResourceProvider.iconLikedRadioUri
        )
    )

    override suspend fun getRandomItems(): MediaMetadataCompat {
        val randomStation = radioTokDb.stationDao().getRandomStation()

        return MediaMetadataCompat.Builder().from(
            itemModel = mapper.invoke(randomStation),
            context = context
        ).build()
    }
}