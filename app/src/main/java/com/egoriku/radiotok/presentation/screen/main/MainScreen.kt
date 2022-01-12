package com.egoriku.radiotok.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.Transition
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.egoriku.radiotok.common.model.RadioItemModel
import com.egoriku.radiotok.extension.noRippleClickable
import com.egoriku.radiotok.presentation.RadioViewModel
import com.egoriku.radiotok.presentation.screen.feed.FeedScreen
import com.egoriku.radiotok.presentation.screen.main.ui.PlayerControls
import com.egoriku.radiotok.presentation.screen.main.ui.RadioLogo
import com.egoriku.radiotok.presentation.screen.main.ui.actions.LikeAction
import com.egoriku.radiotok.presentation.state.RadioPlaybackState
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.placeholder.material.placeholder
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

object MainScreen : Screen {

    @Composable
    override fun Content() {
        val radioViewModel = getViewModel<RadioViewModel>()

        val currentPlayingRadio by radioViewModel.currentPlayingRadio.collectAsState()
        val playbackState by radioViewModel.playbackState.collectAsState()

        val controlsActions = remember { PlayerControlsActions(radioViewModel) }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 72.dp)
                ) {
                    Navigator(screen = FeedScreen)
                }

                RadioPlayer(
                    modifier = Modifier,
                    radioItem = currentPlayingRadio,
                    playbackState = playbackState,
                    playerControlsActions = controlsActions
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadioPlayer(
    modifier: Modifier,
    radioItem: RadioItemModel,
    playbackState: RadioPlaybackState,
    playerControlsActions: PlayerControlsActions
) {
    val coroutineScope = rememberCoroutineScope()

    val componentHeight by remember { mutableStateOf(2000f) }
    val swipeableState = rememberSwipeableState("Bottom")
    val anchors = mapOf(0f to "Bottom", componentHeight to "Top")

    val progress = (swipeableState.offset.value / componentHeight)

    MotionLayout(
        start = constraintStart(),
        end = constraintEnd(),
        transition = transition(),
        progress = progress,
        modifier = modifier.fillMaxSize(),
        // debug = EnumSet.of(MotionLayoutDebugFlags.SHOW_ALL)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .layoutId("box")
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    reverseDirection = true,
                    orientation = Orientation.Vertical
                )
                .noRippleClickable {
                    coroutineScope.launch {
                        when (swipeableState.currentValue) {
                            "Bottom" -> swipeableState.animateTo("Top")
                        }
                    }
                }
        )

        val bottomRadius = motionProperties("bg").value.int("bottomRadius").dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = bottomRadius, bottomEnd = bottomRadius))
                .background(MaterialTheme.colors.primary)
                .layoutId("bg")
        )

        Text(
            modifier = Modifier
                .layoutId("title")
                .placeholder(radioItem.id.isEmpty()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = radioItem.title,
            fontSize = motionProperties("title").value.fontSize("textSize"),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            modifier = Modifier
                .layoutId("subtitle")
                .placeholder(radioItem.id.isEmpty()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = radioItem.subTitle,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.caption
        )
        RadioLogo(
            modifier = Modifier.layoutId("logo"),
            url = radioItem.icon,
            borderSize = motionProperties("logo").value.int("borderSize")
        )
        LikeAction(
            modifier = Modifier.layoutId("like"),
            tint = MaterialTheme.colors.onPrimary,
            onClick = playerControlsActions.toggleFavoriteEvent,
            isLiked = playbackState.isLiked
        )
        PlayerControls(
            modifier = Modifier
                .layoutId("playerControls")
                .padding(vertical = 16.dp),
            isPlaying = playbackState.isPlaying,
            isLiked = playbackState.isLiked,
            isError = playbackState.isError,
            playerControlsActions = playerControlsActions
        )
    }
}

@Composable
private fun constraintStart() = ConstraintSet(
    """
            {
  box: {
    height: 72,
    width: 'spread',
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    bottom: ['parent', 'bottom']
  },
  logo: {
    width: 40,
    height: 40,
    top: ['box', 'top'],
    start: ['box', 'start', 16],
    bottom: ['box', 'bottom'],
    custom: {
      borderSize: 0
    }
  },
  title: {
    height: { value: 'wrap' },
    width: 'spread',
    start: ['logo', 'end', 16],
    end: ['like', 'start'],
    top: ['logo', 'top'],
    bottom: ['subtitle', 'top'],
    custom: {
      textSize: 12
    }
  },
  subtitle: {
    height: { value: 'wrap' },
    width: 'spread',
    start: ['logo', 'end', 16],
    end: ['like', 'start'],
    top: ['title', 'bottom'],
    bottom: ['logo', 'bottom']
  },
  like: {
    width: { value: 'wrap' },
    height: { value: 'wrap' },
    start: ['title', 'end'],
    end: ['parent', 'end', 16],
    top: ['title', 'top'],
    bottom: ['subtitle', 'bottom']
  },
  playerControls: {
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    top: ['parent', 'bottom'],
    alpha: 0.0
  },
  bg: {
    height: 72,
    width: 'spread',
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    bottom: ['parent', 'bottom'],
    custom: {
      bottomRadius: 0
    }
  }
}"""
)

@Composable
private fun constraintEnd() = ConstraintSet(
    """
            {
  box: {
    height: { value: 'parent' },
    width: "spread",
    top: ['parent', 'top'],
    bottom: ['parent', 'bottom'],
    start: ['parent', 'start'],
    end: ['parent', 'end']
  },
  title: {
    width: { value: 'spread' }, 
    height: { value: 'wrap' },
    start: ['parent', 'start', 32],
    end: ['parent', 'end', 32],
    bottom: ['subtitle', 'top', 32],
    custom: {
      textSize: 25
    }
  },
  subtitle: {
    width: 'spread',
    height: { value: 'wrap' },
    start: ['parent', 'start', 32],
    end: ['parent', 'end', 32],
    bottom: ['playerControls', 'top', 32]
  },
  logo: {
    width: 300,
    height: 300,
    top: ['parent', 'top'],
    bottom: ['title', 'top'],
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    custom: {
      borderSize: 10
    }
  },
  like: {
    width: { value: 'wrap' },
    height: { value: 'wrap' },
    start: ['parent', 'end'],
    top: ['title', 'top'],
    bottom: ['subtitle', 'bottom'],
    alpha: 0.0
  },
  playerControls: {
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    bottom: ['parent', 'bottom'],
    alpha: 1.0
  },
  bg: {
    width: 'spread', 
    height: 'spread',
    top: ['box', 'top'],
    bottom: ['playerControls', 'top'],
    start: ['parent', 'start'],
    end: ['parent', 'end'],
    custom: {
      bottomRadius: 30
    }
  }
}"""
)

@Composable
private fun transition() = Transition(
    """
            {
              KeyFrames: {
                KeyPositions: [
                {
                   type: 'deltaRelative',
                   target: ['logo'],
                   frames: [15],
                   percentX: [0.1],
                   percentY: [0.2]
                }
                ]
              }
            }
            """
)