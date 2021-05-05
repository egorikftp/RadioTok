package com.egoriku.radiotok.radioplayer.listener

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventHandler {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    suspend fun playNext() {
        _event.emit(Event.PlayNext)
    }

    sealed class Event {
        object PlayNext : Event()
    }
}