package com.egoriku.radiotok.presentation.screen.feed

import com.egoriku.radiotok.domain.model.Feed

sealed class FeedState {
    object Loading : FeedState()
    data class Success(val feed: Feed) : FeedState()
    object Error : FeedState()
}