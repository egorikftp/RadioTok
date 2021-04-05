package com.egoriku.radiotok.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.R

@Preview(showBackground = true, backgroundColor = 0xFFF4EFE7)
@Composable
private fun PlayPauseButtonPreview() {
    CircleIconButtonLarge(
        background = MaterialTheme.colors.primary,
        icon = painterResource(R.drawable.ic_play_arrow)
    ) {}
}

@Composable
fun CircleIconButtonLarge(
    background: Color,
    icon: Painter,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(background, CircleShape)
            .clickable(
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick() }
            )
    ) {
        Icon(painter = icon, contentDescription = null)
    }
}