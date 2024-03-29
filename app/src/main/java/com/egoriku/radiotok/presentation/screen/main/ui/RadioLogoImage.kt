package com.egoriku.radiotok.presentation.screen.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.Transformation
import com.egoriku.radiotok.R

@Composable
fun RadioLogoImage(
    modifier: Modifier = Modifier,
    data: Any,
    crossfade: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = stringResource(id = R.string.cc_radio_logo_success),
    transformations: List<Transformation> = emptyList()
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .size(size = Size.ORIGINAL)
            .data(data = data)
            .crossfade(crossfade)
            .transformations(transformations)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        error = {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_radio),
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = stringResource(id = R.string.cc_radio_logo_error)
            )
        }
    )
}