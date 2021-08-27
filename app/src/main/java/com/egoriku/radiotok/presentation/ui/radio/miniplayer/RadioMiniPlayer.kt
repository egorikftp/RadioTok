package com.egoriku.radiotok.presentation.ui.radio.miniplayer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.extension.noRippleClickable
import com.egoriku.radiotok.presentation.ControlsActions
import com.egoriku.radiotok.presentation.state.RadioPlaybackState
import com.egoriku.radiotok.presentation.ui.radio.actions.LikeAction
import com.egoriku.radiotok.presentation.ui.radio.miniplayer.component.RadioLogoSmall
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun RadioMiniPlayer(
    modifier: Modifier = Modifier,
    radioItem: RadioItemModel,
    playbackState: RadioPlaybackState,
    controlsActions: ControlsActions,
    fraction: Float,
    clickable: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .graphicsLayer {
                alpha = 1f - fraction
            }
            .noRippleClickable(onClick = onClick, enabled = clickable),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioLogoSmall(
            modifier = Modifier.padding(start = 16.dp),
            placeholder = Modifier.placeholder(
                visible = radioItem.id.isEmpty()
            ),
            url = radioItem.icon
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
                .placeholder(visible = radioItem.id.isEmpty()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = radioItem.name,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.caption
        )

        LikeAction(
            modifier = Modifier.padding(end = 16.dp),
            tint = MaterialTheme.colors.onPrimary,
            onClick = controlsActions.toggleFavoriteEvent,
            isLiked = playbackState.isLiked
        )
    }
}