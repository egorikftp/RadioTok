package com.egoriku.radiotok.presentation.screen.main.ui.actions

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
fun NotInterestedActionPreview() {
    RadioTokTheme {
        NotInterestedAction {}
    }
}

@Composable
fun NotInterestedAction(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_not_interested),
        contentDescription = stringResource(id = R.string.cc_not_interested)
    ) {
        onClick()
    }
}