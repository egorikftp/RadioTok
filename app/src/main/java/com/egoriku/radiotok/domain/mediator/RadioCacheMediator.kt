package com.egoriku.radiotok.domain.mediator

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.domain.repository.IRadioFetchNetworkRepository
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.repository.IMediaItemRepository

class RadioCacheMediator(
    private val radioFetchNetworkRepository: IRadioFetchNetworkRepository,
    private val mediaItemRepository: IMediaItemRepository
) : IRadioCacheMediator {

    private var _radios = emptyList<RadioItemModel>()

    override suspend fun getAllRadioStations(): List<RadioItemModel> {
        checkCacheOrLoad()

        return _radios
    }

    override suspend fun getMediaBrowserItemsBy(mediaPath: MediaPath): List<MediaBrowserCompat.MediaItem> {
        checkCacheOrLoad()

        return when (mediaPath) {
            is MediaPath.Root -> mediaItemRepository.getRootItems()
            else -> emptyList()
        }
    }

    override suspend fun getMediaMetadataBy(mediaPath: MediaPath): List<MediaMetadataCompat> {
        return when (mediaPath) {
            is MediaPath.RandomRadio -> mediaItemRepository.getRandomItems(_radios)
            is MediaPath.LikedRadio -> emptyList()
            else -> emptyList()
        }
    }

    private suspend fun checkCacheOrLoad() {
        // TODO: 2.05.21 Add DB check

        if (_radios.isEmpty()) {
            _radios = radioFetchNetworkRepository.load()
        }
    }
}