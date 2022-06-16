package ru.mintrocket.mintpermissions.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach

class Event<T>(private val content: T) {

    private var handled = false

    fun content() = if (handled) {
        null
    } else {
        handled = true
        content
    }
}

fun <T> Flow<Event<T>>.onEachEvent(action: suspend (T) -> Unit): Flow<Event<T>> = onEach {
    it.content()?.let { action(it) }
}

fun <T> Flow<Event<T>?>.onEachEventNotNull(action: suspend (T) -> Unit): Flow<Event<T>?> = onEach {
    it?.content()?.let { action(it) }
}


fun <T> Flow<Event<T>?>.mapEventNotNull(): Flow<T> = mapNotNull {
    it?.content()
}

fun <T> Flow<Event<T>>.mapEvent(): Flow<T> = mapNotNull {
    it.content()
}