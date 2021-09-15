package com.egoriku.radiotok.domain.mediator

import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaMetadataCompat
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.usecase.IRadioCacheUseCase
import com.egoriku.radiotok.radioplayer.data.CurrentRadioQueueHolder
import com.egoriku.radiotok.radioplayer.data.mediator.IRadioCacheMediator
import com.egoriku.radiotok.radioplayer.ext.from
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.model.MediaPath.*
import com.egoriku.radiotok.radioplayer.model.MediaPath.ShuffleAndPlayRoot.ShuffleLiked
import com.egoriku.radiotok.radioplayer.model.MediaPath.ShuffleAndPlayRoot.ShuffleRandom
import com.egoriku.radiotok.radioplayer.repository.IMediaItemRepository

class RadioCacheMediator(
    private val radioCacheUseCase: IRadioCacheUseCase,
    private val mediaItemRepository: IMediaItemRepository,
    private val currentRadioQueueHolder: CurrentRadioQueueHolder
) : IRadioCacheMediator {

    override suspend fun playSingle(id: String) {
        logD("playSingle with id: $id")
        checkCacheOrLoad()

        currentRadioQueueHolder.updateQueue(
            mediaPath = Single,
            stations = listOf(mediaItemRepository.loadByStationId(id))
        )
    }

    override suspend fun playNextRandom() {
        updatePlaylist(mediaPath = currentRadioQueueHolder.currentMediaPath)
    }

    override suspend fun getMediaBrowserItemsBy(mediaPath: MediaPath): List<MediaItem> {
        checkCacheOrLoad()

        return when (mediaPath) {
            is Root -> mediaItemRepository.getRootItems()
            is ShuffleAndPlayRoot -> mediaItemRepository.getShuffleAndPlayItems()
            is PersonalPlaylistsRoot -> mediaItemRepository.getPersonalPlaylistsItems()
            is PersonalPlaylistsRoot.Liked -> mediaItemRepository.getLikedItems()
            is PersonalPlaylistsRoot.RecentlyPlayed -> mediaItemRepository.getRecentlyPlayedItems()
            is PersonalPlaylistsRoot.Disliked -> mediaItemRepository.getDislikedItems()
            is SmartPlaylistsRoot -> mediaItemRepository.getSmartPlaylistsItems()
            is SmartPlaylistsRoot.LocalStations -> mediaItemRepository.getLocalItems()
            is SmartPlaylistsRoot.TopClicks -> mediaItemRepository.getTopClicksItems()
            is SmartPlaylistsRoot.TopVote -> mediaItemRepository.getTopVoteItems()
            is SmartPlaylistsRoot.ChangedLately -> mediaItemRepository.getChangedLatelyItems()
            is SmartPlaylistsRoot.Playing -> mediaItemRepository.getPlayingItems()
            is CatalogRoot -> mediaItemRepository.getCatalogItems()
            is CatalogRoot.ByTags -> mediaItemRepository.getCatalogTags()
            is CatalogRoot.ByCountries -> mediaItemRepository.getCatalogCountries()
            is CatalogRoot.ByLanguages -> mediaItemRepository.getCatalogLanguages()
            else -> throw IllegalArgumentException()
        }
    }

    override suspend fun getMediaMetadataBy(mediaPath: MediaPath): MediaMetadataCompat {
        checkCacheOrLoad()

        return when (mediaPath) {
            is ShuffleRandom -> mediaItemRepository.getRandomItem()
            is ShuffleLiked -> mediaItemRepository.getLikedItem()
            else -> throw IllegalArgumentException()
        }
    }

    override suspend fun updatePlaylist(mediaPath: MediaPath) {
        when (mediaPath) {
            PlayLiked -> {
                val likedItems = mediaItemRepository.getLikedItemsTest().map {
                    MediaMetadataCompat.Builder().from(entity = it).build()
                }

                currentRadioQueueHolder.updateQueue(mediaPath = mediaPath, stations = likedItems)
            }
            ShuffleRandom, ShuffleLiked -> {
                checkCacheOrLoad()

                currentRadioQueueHolder.updateQueue(
                    mediaPath = mediaPath,
                    stations = listOf(getMediaMetadataBy(mediaPath = mediaPath))
                )
            }
        }
    }

    private suspend fun checkCacheOrLoad() {
        radioCacheUseCase.preCacheStations()
    }
}