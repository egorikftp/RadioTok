package com.egoriku.radiotok.foundation.button

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.radiotok.R
import com.egoriku.radiotok.presentation.theme.RadioTokTheme

@Preview(showBackground = true)
@Composable
private fun LikedIconButtonPreview() {
    RadioTokTheme {
        Row {
            LikedIconButton(
                tint = MaterialTheme.colors.secondary,
                isLiked = true
            ) {}
            LikedIconButton(
                tint = MaterialTheme.colors.secondary,
                isLiked = false
            ) {}
        }
    }
}

@Composable
fun LikedIconButton(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colors.onPrimary,
    isLiked: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        when {
            isLiked -> Icon(
                painter = painterResource(R.drawable.ic_favorite),
                tint = tint,
                contentDescription = stringResource(id = R.string.cc_remove_favorite)
            )
            else -> Icon(
                painter = painterResource(R.drawable.ic_favorite_border),
                tint = tint,
                contentDescription = stringResource(id = R.string.cc_add_favorite)
            )
        }
    }
}