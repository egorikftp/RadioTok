package com.egoriku.radiotok.presentation.ui.radio.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R
import com.egoriku.radiotok.presentation.ControlsActions

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    isLiked: Boolean,
    controlsActions: ControlsActions
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { controlsActions.dislikeRadioStationEvent() },
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_not_interested),
                contentDescription = "Not interested"
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { controlsActions.tuneRadiosEvent() },
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_tune),
                    contentDescription = "Tune"
                )
            }

            PlayPauseButton(isPlaying = isPlaying, onClick = controlsActions.playPauseEvent)

            IconButton(
                onClick = { controlsActions.nextRadioEvent() },
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_skip_next),
                    contentDescription = "Skip Next"
                )
            }
        }

        IconButton(
            onClick = { controlsActions.addRemoveFavoriteEvent() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
        ) {
            if (isLiked) {
                Image(
                    painter = painterResource(R.drawable.ic_favorite),
                    contentDescription = "Add favorite"
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.ic_favorite_border),
                    contentDescription = "Remove favorite"
                )
            }
        }
    }
}