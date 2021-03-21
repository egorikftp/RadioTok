package com.egoriku.radiotok

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.radiotok.foundation.player.AudioPlayer
import com.egoriku.radiotok.ui.theme.RadioTokTheme
import com.egoriku.radiotok.util.DynamicThemePrimaryColorsFromImage
import com.egoriku.radiotok.util.constrastAgainst
import com.egoriku.radiotok.util.rememberDominantColorState
import com.egoriku.radiotok.viewmodel.RadioViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewModel: RadioViewModel by viewModel()

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RadioTokTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val playerModel by viewModel.currentPlayerModel.collectAsState()

                    AudioPlayer(playerModel = playerModel, onNext = {
                        viewModel.nextStation()
                    })

                    val surfaceColor = MaterialTheme.colors.surface

                    val dominantColorState = rememberDominantColorState { color ->
                        color.constrastAgainst(surfaceColor) >= 3f
                    }

                    DynamicThemePrimaryColorsFromImage(dominantColorState) {
                        val selectedImageUrl = playerModel.icon

                        if (selectedImageUrl.isNotEmpty()) {
                            LaunchedEffect(selectedImageUrl) {
                                dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                            }
                        } else {
                            dominantColorState.reset()
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            BoxWithConstraints(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(300.dp)
                                    .shadow(2.dp, CircleShape)
                                    .background(
                                        color = dominantColorState.color,
                                        shape = CircleShape
                                    )
                            ) {
                                CoilImage(
                                    fadeIn = true,
                                    data = playerModel.icon,
                                    contentDescription = null,
                                    modifier = Modifier.width(170.dp),
                                    error = {
                                        Image(
                                            modifier = Modifier.size(100.dp),
                                            painter = painterResource(id = R.drawable.ic_radio),
                                            contentDescription = null
                                        )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.size(32.dp))
                            Text(
                                text = playerModel.name,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                            Spacer(modifier = Modifier.size(32.dp))

                            Spacer(modifier = Modifier.size(100.dp))

                            Button(
                                onClick = { viewModel.nextStation() },
                                shape = CircleShape
                            ) {
                                Text(
                                    text = "Next station",
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 8.dp,
                                        bottom = 8.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}