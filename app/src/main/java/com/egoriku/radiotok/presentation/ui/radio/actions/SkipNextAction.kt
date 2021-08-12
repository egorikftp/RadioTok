package com.egoriku.radiotok.presentation.ui.radio.actions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.radiotok.R
import com.egoriku.radiotok.foundation.button.IconButton
import com.egoriku.radiotok.presentation.ui.RadioTokTheme

@Preview(showBackground = true)
@Composable
fun SkipNextActionPreview() {
    RadioTokTheme {
        SkipNextAction {}
    }
}

@Composable
fun SkipNextAction(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_skip_next),
        contentDescription = stringResource(id = R.string.cc_skip_next)
    ) {
        onClick()
    }
}