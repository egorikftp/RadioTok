package com.egoriku.radiotok.presentation.ui.radio.actions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.radiotok.R
import com.egoriku.radiotok.foundation.button.IconButton
import com.egoriku.radiotok.presentation.theme.RadioTokTheme

@Preview(showBackground = true)
@Composable
fun TuneActionPreview() {
    RadioTokTheme {
        TuneAction {}
    }
}

@Composable
fun TuneAction(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        painter = painterResource(R.drawable.ic_tune),
        contentDescription = stringResource(id = R.string.cc_tune)
    ) {
        onClick()
    }
}