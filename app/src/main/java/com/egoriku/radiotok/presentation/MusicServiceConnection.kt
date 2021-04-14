package com.egoriku.radiotok.presentation

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.egoriku.radiotok.domain.model.RadioItemModel
import com.egoriku.radiotok.exoplayer.PlayerConstants.MEDIA_ROOT_ID
import com.egoriku.radiotok.exoplayer.PlayerConstants.NETWORK_ERROR
import com.egoriku.radiotok.exoplayer.ext.isPlayEnabled
import com.egoriku.radiotok.exoplayer.ext.isPlaying
import com.egoriku.radiotok.exoplayer.ext.isPrepared
import com.egoriku.radiotok.exoplayer.notification.CustomAction
import com.egoriku.radiotok.exoplayer.service.RadioService
import com.egoriku.radiotok.extensions.EMPTY
import com.egoriku.radiotok.extensions.ResultOf
import com.egoriku.radiotok.presentation.state.RadioPlaybackState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

interface IMusicServiceConnection {
    val isConnected: SharedFlow<ResultOf<Boolean>>
    val networkError: SharedFlow<ResultOf<Boolean>>

    val playbackState: StateFlow<RadioPlaybackState>
    val currentPlayingRadio: StateFlow<RadioItemModel>

    val transportControls: MediaControllerCompat.TransportControls
}

internal class MusicServiceConnection(context: Context) : IMusicServiceConnection {

    private val _isConnected = MutableSharedFlow<ResultOf<Boolean>>()
    override val isConnected = _isConnected.asSharedFlow()

    private val _networkError = MutableSharedFlow<ResultOf<Boolean>>()
    override val networkError = _networkError.asSharedFlow()

    private val _playbackState = MutableStateFlow(RadioPlaybackState())
    override val playbackState = _playbackState.asStateFlow()

    private val _currentPlayingRadio = MutableStateFlow(RadioItemModel())
    override val currentPlayingRadio = _currentPlayingRadio.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main)

    private var mediaController: MediaControllerCompat by Delegates.notNull()

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        ComponentName(context, RadioService::class.java),
        mediaBrowserConnectionCallback,
        null
    ).apply {
        connect()

        subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
        })
    }

    override val transportControls: MediaControllerCompat.TransportControls
        get() = mediaController.transportControls

    private inner class MediaBrowserConnectionCallback(
        private val context: Context
    ) : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }

            scope.launch {
                _isConnected.emit(ResultOf.Success(true))
            }
        }

        override fun onConnectionSuspended() {
            scope.launch {
                _isConnected.emit(ResultOf.Failure("The connection was suspended"))
            }
        }

        override fun onConnectionFailed() {
            scope.launch {
                _isConnected.emit(ResultOf.Failure("Couldn't connect to media browser"))
            }
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            Log.d("kek", "onPlaybackStateChanged = $state")
            scope.launch {
                _playbackState.value = when (state) {
                    null -> RadioPlaybackState()
                    else -> {
                        RadioPlaybackState(
                            isPlaying = state.isPlaying,
                            isPrepared = state.isPrepared,
                            isPlayEnabled = state.isPlayEnabled,
                            isLiked = state.customActions.first {
                                it.action == CustomAction.ACTION_TOGGLE_FAVORITE
                            }.extras.getBoolean("IS_LIKED")
                        )
                    }
                }
            }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            Log.d("kek", "onMetadataChanged $metadata")

            scope.launch {
                _currentPlayingRadio.value = when (metadata) {
                    null -> RadioItemModel()
                    else -> {
                        val description = metadata.description

                        RadioItemModel(
                            id = description.mediaId ?: EMPTY,
                            name = description.title.toString(),
                            streamUrl = description.mediaUri.toString(),
                            icon = description.iconUri.toString()
                        )
                    }
                }
            }
        }

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            super.onSessionEvent(event, extras)
            when (event) {
                NETWORK_ERROR -> scope.launch {
                    _networkError.emit(
                        ResultOf.Failure(
                            "Couldn't connect to the server. Please check your internet connection.",
                        )
                    )
                }
            }
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}