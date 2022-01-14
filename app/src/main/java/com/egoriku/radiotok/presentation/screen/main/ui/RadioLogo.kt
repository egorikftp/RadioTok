package com.egoriku.radiotok.presentation.screen.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation

@Composable
fun RadioLogo(
    modifier: Modifier = Modifier,
    url: String,
    borderSize: Int
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(300.dp)
            .shadow(30.dp, CircleShape)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = CircleShape
            )
            .then(
                when {
                    borderSize > 0 -> Modifier.border(borderSize.dp, MaterialTheme.colors.primary, CircleShape)
                    else -> Modifier
                }
            )


    ) {
        RadioLogoImage(
            modifier = Modifier
                .size(width = maxWidth / 2f, height = maxHeight / 2)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                ),
            data = url,
            transformations = listOf(CircleCropTransformation())
        )
    }
}