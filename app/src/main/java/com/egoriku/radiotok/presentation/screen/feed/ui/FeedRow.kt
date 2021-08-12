package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun FeedRow(
    title: String,
    feedItems: LazyListScope.() -> Unit
) {
    FeedHeader(title = title)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        content = feedItems
    )
}