package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun LazyListScope.MoreItem(
    enabled: Boolean,
    onClick: () -> Unit
) {
    if (enabled) {
        item {
            LastItem(onClick = onClick)
        }
    }
}

@Composable
fun LastItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(CircleShape)
                .background(MaterialTheme.colors.secondary)
                .clickable(onClick = onClick)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                tint = MaterialTheme.colors.onSecondary,
                contentDescription = null
            )
        }
    }
}