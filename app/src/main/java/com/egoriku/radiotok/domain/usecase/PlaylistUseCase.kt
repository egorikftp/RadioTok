package com.egoriku.radiotok.domain.usecase

import com.egoriku.radiotok.R
import com.egoriku.radiotok.common.provider.IStringResourceProvider
import com.egoriku.radiotok.datasource.datasource.IRadioInfoDataSource
import com.egoriku.radiotok.datasource.datasource.playlist.ILocalStationsDataSource
import com.egoriku.radiotok.db.RadioTokDb
import com.egoriku.radiotok.presentation.screen.playlist.model.Playlist
import com.egoriku.radiotok.radioplayer.model.MediaPath
import com.egoriku.radiotok.radioplayer.model.MediaPath.Companion.fromParentIdOrNull
import com.egoriku.radiotok.radioplayer.model.MediaPath.PersonalPlaylistsRoot.Disliked
import com.egoriku.radiotok.radioplayer.model.MediaPath.PersonalPlaylistsRoot.Liked
import com.egoriku.radiotok.radioplayer.model.MediaPath.PersonalPlaylistsRoot.RecentlyPlayed
import com.egoriku.radiotok.radioplayer.repository.RadioEntityToModelMapper
import kotlinx.coroutines.coroutineScope

class PlaylistUseCase(
    private val db: RadioTokDb,
    private val stringResource: IStringResourceProvider,
    private val radioInfoDataSource: IRadioInfoDataSource,
    private val localStationsDataSource: ILocalStationsDataSource
) {

    private val entityMapper = RadioEntityToModelMapper()

    suspend fun loadPlaylist(id: String): Result<Playlist> = coroutineScope {
        val mediaPath = fromParentIdOrNull(id)

        if (mediaPath == null) {
            Result.failure(
                exception = IllegalArgumentException("mediaPath with $id not found")
            )
        } else {
            when (mediaPath) {
                Liked -> {
                    val stations = radioInfoDataSource.loadByIds(
                        ids = db.stationDao().getLikedStationsIds()
                    ).map(entityMapper)

                    Result.success(
                        Playlist(
                            id = id,
                            icon = R.drawable.ic_favorite,
                            title = stringResource.liked,
                            radioStations = stations
                        )
                    )
                }
                RecentlyPlayed -> Result.success(
                    Playlist(
                        id = id,
                        title = stringResource.recentlyPlayed,
                        icon = R.drawable.ic_history,
                        radioStations = emptyList()
                    )
                )
                Disliked -> {
                    val stations = radioInfoDataSource.loadByIds(
                        ids = db.stationDao().getDislikedStationsIds()
                    ).map(entityMapper)

                    Result.success(
                        Playlist(
                            id = id,
                            icon = R.drawable.ic_not_interested,
                            title = stringResource.disliked,
                            radioStations = stations
                        )
                    )
                }
                MediaPath.SmartPlaylistsRoot.LocalStations -> {
                    val stations = localStationsDataSource.load().map(entityMapper)

                    Result.success(
                        Playlist(
                            id = id,
                            icon = R.drawable.ic_local,
                            title = stringResource.localStations,
                            radioStations = stations
                        )
                    )
                }
                else -> Result.failure(IllegalArgumentException(id))
            }
        }
    }
}