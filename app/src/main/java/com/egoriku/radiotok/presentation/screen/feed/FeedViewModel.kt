package com.egoriku.radiotok.presentation.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.domain.usecase.FeedUseCase
import com.egoriku.radiotok.presentation.IMusicServiceConnection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val serviceConnection: IMusicServiceConnection,
    private val feedUseCase: FeedUseCase
) : ViewModel() {

    private val _feedState = MutableStateFlow<FeedState>(FeedState.Loading)

    val feedState = _feedState.asStateFlow()

    init {
        logD("FeedViewModel created")

        viewModelScope.launch {
            _feedState.emit(FeedState.Loading)

            val feed = feedUseCase.loadFeed()

            _feedState.emit(FeedState.Success(feed = feed))
        }
    }

    fun playFromMediaId(mediaId: String) {
        logD("playFromMediaId: $mediaId")

        serviceConnection.transportControls.playFromMediaId(mediaId, null)
    }
}