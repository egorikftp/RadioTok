package com.egoriku.radiotok.radioplayer.repository

import android.support.v4.media.MediaBrowserCompat
import androidx.core.net.toUri
import com.egoriku.mediaitemdsl.browsableMediaItem
import com.egoriku.mediaitemdsl.playableMediaItem
import com.egoriku.radiotok.common.provider.IBitmapProvider
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.datasource.IEntitySourceFactory
import com.egoriku.radiotok.datasource.entity.params.MetadataParams
import com.egoriku.radiotok.datasource.entity.params.PlaylistParams
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.radioplayer.model.MediaPath.*
import kotlinx.coroutines.runBlocking

internal class MediaItemRepository(
    private val bitmapProvider: IBitmapProvider,
    private val stringResource: IStringResourceProvider,
    private val radioTokDb: RadioTokDb,
    private val entitySourceFactory: IEntitySourceFactory
) : IMediaItemRepository {

    override fun getRootItems() = listOf(
        browsableMediaItem {
            id = ShuffleAndPlayRoot.path
            title = stringResource.shuffleAndPlay
            iconBitmap = bitmapProvider.icRadioWaves
        },
        browsableMediaItem {
            id = PersonalPlaylistsRoot.path
            title = stringResource.personalPlaylists
            iconBitmap = bitmapProvider.icPersonal
        },
        browsableMediaItem {
            id = SmartPlaylistsRoot.path
            title = stringResource.smartPlaylists
            iconBitmap = bitmapProvider.icSmartPlaylist
        },
        browsableMediaItem {
            id = CatalogRoot.path
            title = stringResource.catalog
            iconBitmap = bitmapProvider.icCollection
        }
    )

    @OptIn(ExperimentalStdlibApi::class)
    override fun getShuffleAndPlayItems() = buildList {
        add(
            playableMediaItem {
                id = ShuffleAndPlayRoot.ShuffleRandom.path
                title = stringResource.randomRadio
                iconBitmap = bitmapProvider.icShuffleRound
            }
        )

        runBlocking {
            val mediaItem = if (radioTokDb.stationDao().likedStationsCount() > 0) {
                playableMediaItem {
                    id = ShuffleAndPlayRoot.ShuffleLiked.path
                    title = stringResource.likedRadio
                    iconBitmap = bitmapProvider.icLikedRound
                }
            } else {
                browsableMediaItem {
                    id = ShuffleAndPlayRoot.ShuffleLiked.path
                    title = stringResource.likedRadio
                    iconBitmap = bitmapProvider.icLikedRound
                }
            }

            add(mediaItem)
        }
    }

    override fun getPersonalPlaylistsItems() = listOf(
        browsableMediaItem {
            id = PersonalPlaylistsRoot.Liked.path
            title = stringResource.liked
            iconBitmap = bitmapProvider.icLikedRound
        },
        browsableMediaItem {
            id = PersonalPlaylistsRoot.RecentlyPlayed.path
            title = stringResource.recentlyPlayed
            iconBitmap = bitmapProvider.icHistoryRound
        },
        browsableMediaItem {
            id = PersonalPlaylistsRoot.Disliked.path
            title = stringResource.disliked
            iconBitmap = bitmapProvider.icDislikedRound
        }
    )

    override fun getLikedItems() = runBlocking {
        listOf(
            playableMediaItem {
                id = PlayLiked.path
                title = "Play All"
            }
        ) + entitySourceFactory.loadByIds(
            ids = radioTokDb.stationDao().getLikedStationsIds()
        ).map {
            playableMediaItem {
                id = it.stationUuid
                title = it.name
                subTitle = it.tags
                iconUri = it.icon.toUri()

                appearance {
                    showAsList = true
                }
            }
        }
    }

    override fun getLikedItemsTest() = runBlocking {
        entitySourceFactory.loadByIds(
            ids = radioTokDb.stationDao().getLikedStationsIds()
        )
    }

    override fun getRecentlyPlayedItems(): List<MediaBrowserCompat.MediaItem> {
        return emptyList()
    }

    override fun getDislikedItems() = runBlocking {
        entitySourceFactory.loadByIds(
            ids = radioTokDb.stationDao().getDislikedStationsIds()
        ).map {
            playableMediaItem {
                id = it.stationUuid
                title = it.name
                iconUri = it.icon.toUri()
                subTitle = it.tags
            }
        }
    }

    override fun getSmartPlaylistsItems() = listOf(
        browsableMediaItem {
            id = SmartPlaylistsRoot.LocalStations.path
            title = stringResource.localStations
            iconBitmap = bitmapProvider.icLocalRound
        },
        browsableMediaItem {
            id = SmartPlaylistsRoot.TopClicks.path
            title = stringResource.topClicks
            iconBitmap = bitmapProvider.icTopClicksRound
        },
        browsableMediaItem {
            id = SmartPlaylistsRoot.TopVote.path
            title = stringResource.topVote
            iconBitmap = bitmapProvider.icTopVoteRound
        },
        browsableMediaItem {
            id = SmartPlaylistsRoot.ChangedLately.path
            title = stringResource.changedLately
            iconBitmap = bitmapProvider.icChangedLatelyRound
        },
        browsableMediaItem {
            id = SmartPlaylistsRoot.Playing.path
            title = stringResource.playing
            iconBitmap = bitmapProvider.icPlayingRound
        }
    )

    override fun getLocalItems() = runBlocking {
        entitySourceFactory.loadBy(params = PlaylistParams.LocalStations).map { radioEntity ->
            playableMediaItem {
                id = radioEntity.stationUuid
                title = radioEntity.name
                iconUri = radioEntity.icon.toUri()
            }
        }
    }

    override fun getCatalogItems() = listOf(
        browsableMediaItem {
            id = CatalogRoot.ByTags.path
            title = stringResource.byTags
            iconBitmap = bitmapProvider.icTagsRound
        },
        browsableMediaItem {
            id = CatalogRoot.ByCountries.path
            title = stringResource.byCountry
            iconBitmap = bitmapProvider.icCountryRounded
        },
        browsableMediaItem {
            id = CatalogRoot.ByLanguages.path
            title = stringResource.byLanguage
            iconBitmap = bitmapProvider.icLanguageRound
        }
    )

    override fun getTopClicksItems() = runBlocking {
        entitySourceFactory.loadBy(params = PlaylistParams.TopClicks).map { radioEntity ->
            playableMediaItem {
                id = radioEntity.stationUuid
                title = radioEntity.name
                iconUri = radioEntity.icon.toUri()
            }
        }
    }

    override fun getTopVoteItems() = runBlocking {
        entitySourceFactory.loadBy(params = PlaylistParams.TopVote).map { radioEntity ->
            playableMediaItem {
                id = radioEntity.stationUuid
                title = radioEntity.name
                iconUri = radioEntity.icon.toUri()
            }
        }
    }

    override fun getChangedLatelyItems() = runBlocking {
        entitySourceFactory.loadBy(params = PlaylistParams.ChangedLately).map { radioEntity ->
            playableMediaItem {
                id = radioEntity.stationUuid
                title = radioEntity.name
                iconUri = radioEntity.icon.toUri()
            }
        }
    }

    override fun getPlayingItems() = runBlocking {
        entitySourceFactory.loadBy(params = PlaylistParams.PlayingNow).map { radioEntity ->
            playableMediaItem {
                id = radioEntity.stationUuid
                title = radioEntity.name
                iconUri = radioEntity.icon.toUri()
            }
        }
    }

    override fun getCatalogTags() = runBlocking {
        entitySourceFactory.loadBy(MetadataParams.Tags)
            .groupBy { it.name.substring(0, 1) }
            .map {
                browsableMediaItem {
                    id = it.key
                    title = it.key
                    iconUri = bitmapProvider.bgRadioGradient
                    subTitle = stringResource.getStationsCount(it.value.size)

                    appearance {
                        showAsList = true
                    }
                }
            }
    }

    override fun getCatalogCountries() = runBlocking {
        entitySourceFactory.loadBy(params = MetadataParams.Countries).map {
            browsableMediaItem {
                id = it.name
                title = it.name
                subTitle = stringResource.getStationsCount(it.count)

                appearance {
                    showAsList = true
                }
            }
        }
    }

    override fun getCatalogLanguages() = runBlocking {
        entitySourceFactory.loadBy(params = MetadataParams.Languages).map {
            browsableMediaItem {
                id = it.name
                title = it.name
                subTitle = stringResource.getStationsCount(it.count)
                iconUri = bitmapProvider.bgRadioGradient

                appearance {
                    showAsList = true
                }
            }
        }
    }
}