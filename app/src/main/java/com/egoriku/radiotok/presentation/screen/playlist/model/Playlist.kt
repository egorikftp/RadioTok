package com.egoriku.radiotok.presentation.screen.playlist.model

import com.egoriku.radiotok.common.model.RadioItemModel

data class Playlist(
    val id: String,
    val icon: Int,
    val title: String,
    val radioStations: List<RadioItemModel>
)