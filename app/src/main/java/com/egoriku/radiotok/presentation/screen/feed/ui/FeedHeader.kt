package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedHeader(title: String) {
    Text(
        modifier = Modifier.padding(start = 18.dp, top = 8.dp),
        text = title,
        style = MaterialTheme.typography.h6
    )
}