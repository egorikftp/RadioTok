package com.egoriku.radiotok.domain.mediator

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.mediaitemdsl.playableMediaItem
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.usecase.IRadioCacheUseCase
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.model.MediaPath.*
import com.egoriku.radiotok.radioplayer.repository.IMediaItemRepository

class RadioCacheMediator(
    private val radioCacheUseCase: IRadioCacheUseCase,
    private val mediaItemRepository: IMediaItemRepository,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder
) : IRadioCacheMediator {

    override suspend fun loadNextRadio() {
        logD("loadNextRadio")
        checkCacheOrLoad()

        val items = getMediaMetadataBy(mediaPath = currentRadioQueueHolder.currentPath)

        currentRadioQueueHolder.set(items)
    }

    override suspend fun switchToRandomRadios() {
        logD("switchToRandomRadios")

        checkCacheOrLoad()

        currentRadioQueueHolder.currentPath = ShuffleAndPlayRoot.ShuffleRandom
        loadInitial()
    }

    override suspend fun switchToLikedRadios() {
        logD("switchToLikedRadios")

        checkCacheOrLoad()

        currentRadioQueueHolder.currentPath = ShuffleAndPlayRoot.ShuffleLiked
        loadInitial()
    }

    override suspend fun playSingle(id : String) {
        logD("playSingle")
        checkCacheOrLoad()

        currentRadioQueueHolder.currentPath = Single

        currentRadioQueueHolder.set(mediaItemRepository.loadByStationId(id))
    }

    override suspend fun getMediaBrowserItemsBy(mediaPath: MediaPath): List<MediaBrowserCompat.MediaItem> {
        checkCacheOrLoad()

        return when (mediaPath) {
            is Root -> mediaItemRepository.getRootItems()
            is ShuffleAndPlayRoot -> mediaItemRepository.getShuffleAndPlayItems()
            is PersonalPlaylistsRoot -> mediaItemRepository.getPersonalPlaylistsItems()
            is PersonalPlaylistsRoot.Liked -> mediaItemRepository.getLikedItems()
            is PersonalPlaylistsRoot.RecentlyPlayed -> mediaItemRepository.getRecentlyPlayedItems()
            is PersonalPlaylistsRoot.Disliked -> mediaItemRepository.getDislikedItems()
            is SmartPlaylistsRoot -> mediaItemRepository.getSmartPlaylistsItems()
            is CatalogRoot -> mediaItemRepository.getCatalogItems()
            is CatalogRoot.ByTags -> mediaItemRepository.getCatalogTags()
            is CatalogRoot.ByCountries -> mediaItemRepository.getCatalogCountries()
            is CatalogRoot.ByLanguages -> mediaItemRepository.getCatalogLanguages()
            else -> {
                currentRadioQueueHolder.currentPath = mediaPath

                val mediaMetadata = getMediaMetadataBy(mediaPath)

                currentRadioQueueHolder.set(mediaMetadata)

                listOf(playableMediaItem(mediaMetadata))
            }
        }
    }

    override suspend fun getMediaMetadataBy(mediaPath: MediaPath): MediaMetadataCompat {
        checkCacheOrLoad()

        return when (mediaPath) {
            is ShuffleAndPlayRoot.ShuffleRandom -> mediaItemRepository.getRandomItem()
            is ShuffleAndPlayRoot.ShuffleLiked -> mediaItemRepository.getLikedItem()
            else -> throw IllegalArgumentException()
        }
    }

    private suspend fun checkCacheOrLoad() {
        radioCacheUseCase.preCacheStations()
    }

    private suspend fun loadInitial() {
        val items = getMediaMetadataBy(mediaPath = currentRadioQueueHolder.currentPath)

        currentRadioQueueHolder.set(items)
    }
}