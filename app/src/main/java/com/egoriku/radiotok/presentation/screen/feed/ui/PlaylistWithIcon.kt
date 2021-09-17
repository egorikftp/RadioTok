package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.domain.model.section.FeedType
import com.egoriku.radiotok.foundation.HSpacer

@Composable
fun PlaylistWithIcon(
    modifier: Modifier = Modifier,
    collection: FeedType.Playlist,
    onClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier.size(150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick(collection.id) },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = collection.icon),
                contentDescription = null
            )
            HSpacer(16.dp)
            Text(text = collection.name, style = MaterialTheme.typography.body1)
        }
    }
}