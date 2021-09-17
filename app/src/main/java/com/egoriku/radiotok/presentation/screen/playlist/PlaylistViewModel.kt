package com.egoriku.radiotok.presentation.screen.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.usecase.PlaylistUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistUseCase: PlaylistUseCase
) : ViewModel() {

    private val _playlistState = MutableStateFlow<PlaylistState>(PlaylistState.Loading)
    val playlistState = _playlistState.asStateFlow()

    init {
        logD("PlaylistViewModel created")
    }

    fun load(id: String) {
        viewModelScope.launch {
            _playlistState.emit(PlaylistState.Loading)

            val result = playlistUseCase.loadPlaylist(id)

            when {
                result.isFailure -> _playlistState.emit(PlaylistState.Error)
                else -> _playlistState.emit(PlaylistState.Success(playlist = result.getOrThrow()))
            }

        }
    }
}