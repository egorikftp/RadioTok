package com.egoriku.radiotok.presentation.screen.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.presentation.screen.main.PlayerControlsActions
import com.egoriku.radiotok.presentation.screen.main.ui.actions.*

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    isLiked: Boolean,
    isError: Boolean,
    playerControlsActions: PlayerControlsActions
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NotInterestedAction(
            onClick = playerControlsActions.dislikeRadioStationEvent,
            modifier = Modifier.padding(start = 16.dp)
        )

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TuneAction(
                onClick = playerControlsActions.tuneRadiosEvent,
                modifier = Modifier.padding(end = 16.dp)
            )

            PlayPauseAction(
                enable = !isError,
                isPlaying = isPlaying,
                onClick = playerControlsActions.playPauseEvent
            )

            SkipNextAction(
                modifier = Modifier.padding(start = 16.dp),
                onClick = playerControlsActions.nextRadioEvent
            )
        }

        LikeAction(
            modifier = Modifier.padding(end = 16.dp),
            tint = MaterialTheme.colors.secondary,
            onClick = { playerControlsActions.toggleFavoriteEvent() },
            isLiked = isLiked
        )
    }
}