package com.egoriku.radiotok.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import coil.request.ImageRequest
import coil.transform.Transformation
import com.egoriku.radiotok.R

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    data: Any,
    crossfade: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = stringResource(id = R.string.cc_radio_logo_success),
    transformations: List<Transformation> = emptyList(),
    loading: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit = { },
    error: @Composable SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit = { },
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = data)
            .crossfade(crossfade)
            .transformations(transformations)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        loading = loading,
        error = error
    )
}

@Composable
fun NetworkImage(
    modifier: Modifier = Modifier,
    data: Any,
    crossfade: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = stringResource(id = R.string.cc_radio_logo_success),
    transformations: List<Transformation> = emptyList()
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = data)
            .crossfade(crossfade)
            .transformations(transformations)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}