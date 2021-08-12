package com.egoriku.radiotok.presentation.screen.feed.model

sealed class FeedType {

    data class InstantPlay(
        val mediaId: String,
        val name: String,
        val icon: Int
    ) : FeedType()

    data class Playlist(
        val name: String,
        val icon: Int
    ) : FeedType()

    data class SimplePlaylist(val name: String) : FeedType()
}