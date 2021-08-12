package com.egoriku.radiotok.presentation.ui.radio.miniplayer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.egoriku.radiotok.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RadioLogoSmall(
    modifier: Modifier = Modifier,
    placeholder: Modifier = Modifier,
    url: String
) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            transformations(CircleCropTransformation())
        },
        onExecute = { _, _ -> true }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(1.dp, CircleShape)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .then(placeholder)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(id = R.string.cc_radio_logo_success),
            modifier = Modifier.size(48.dp)
        )

        when (painter.state) {
            is ImagePainter.State.Error -> {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_radio),
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = stringResource(id = R.string.cc_radio_logo_error)
                )
            }
            else -> {
            }
        }
    }
}