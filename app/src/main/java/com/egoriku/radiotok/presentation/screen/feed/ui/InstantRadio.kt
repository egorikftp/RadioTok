package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.presentation.screen.feed.model.FeedType.InstantPlay
import com.egoriku.radiotok.presentation.theme.RadioTokTheme

@Preview(showBackground = true)
@Composable
private fun InstantRadioPreview() {
    RadioTokTheme {
        InstantRadio(
            feed = InstantPlay(
                mediaId = "Test",
                name = "Liked",
                icon = R.drawable.ic_favorite
            )
        ) {}
    }
}

@Composable
fun InstantRadio(
    modifier: Modifier = Modifier,
    feed: InstantPlay,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        modifier = modifier
            .height(100.dp)
            .aspectRatio(2.5f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable(onClick = onClick),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(MaterialTheme.colors.onSecondary)
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = feed.icon),
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = feed.name,
                style = MaterialTheme.typography.body1
            )
        }
    }
}