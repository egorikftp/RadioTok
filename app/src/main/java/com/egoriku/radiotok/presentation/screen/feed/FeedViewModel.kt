package com.egoriku.radiotok.presentation.screen.feed

import androidx.lifecycle.ViewModel
import com.egoriku.radiotok.common.ext.logD
import com.egoriku.radiotok.presentation.IMusicServiceConnection

class FeedViewModel(
    private val serviceConnection: IMusicServiceConnection
) : ViewModel() {

    init {
        logD("FeedViewModel created")
    }

    fun playFromMediaId(mediaId: String) {
        logD("playFromMediaId: $mediaId")

        serviceConnection.transportControls.playFromMediaId(mediaId, null)
    }
}