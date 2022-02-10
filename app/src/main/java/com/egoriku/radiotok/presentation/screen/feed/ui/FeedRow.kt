package com.egoriku.radiotok.presentation.screen.feed.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.domain.model.Lane
import com.egoriku.radiotok.extension.noRippleClickable

@Composable
fun FeedRow(
    lane: Lane,
    onMoreActionClick: () -> Unit = {},
    feedItems: LazyListScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 8.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = lane.titleRes),
            style = MaterialTheme.typography.h6
        )
        if (lane.showMore) {
            Text(
                modifier = Modifier.noRippleClickable { onMoreActionClick() },
                text = stringResource(id = R.string.show_all),
                style = MaterialTheme.typography.button
            )
        }
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        content = feedItems
    )
}