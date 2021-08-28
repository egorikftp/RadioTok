package com.egoriku.radiotok.domain.model

import com.egoriku.radiotok.domain.model.section.FeedType

data class Feed(
    val shuffleAndPlay: List<FeedType.InstantPlay>,
    val forYou: List<FeedType.Playlist>,
    val smartPlaylists: List<FeedType.Playlist>,
    val byTags: List<FeedType.SimplePlaylist>,
    val byCountry: List<FeedType.SimplePlaylist>,
    val byLanguage: List<FeedType.SimplePlaylist>
)
