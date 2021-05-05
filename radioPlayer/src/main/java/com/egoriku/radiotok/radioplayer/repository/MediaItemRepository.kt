package com.egoriku.radiotok.radioplayer.repository

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.common.provider.IDrawableResourceProvider
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.radioplayer.ext.createPlayableMediaItem
import com.egoriku.radiotok.radioplayer.ext.from
import com.egoriku.radiotok.radioplayer.model.MediaPath

internal class MediaItemRepository(
    private val context: Context,
    private val drawableResourceProvider: IDrawableResourceProvider,
    private val stringResource: IStringResourceProvider
) : IMediaItemRepository {

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

    override fun getRandomItems(items: List<RadioItemModel>): List<MediaMetadataCompat> {
        return items.shuffled()
            .subList(0, 10)
            .map {
                MediaMetadataCompat.Builder().from(it, context).build()
            }
    }
}