package com.egoriku.radiotok.presentation.screen.playlist

import com.egoriku.radiotok.presentation.screen.playlist.model.Playlist

sealed class PlaylistState {
    object Loading : PlaylistState()
    data class Success(val playlist: Playlist) : PlaylistState()
    object Error : PlaylistState()
}