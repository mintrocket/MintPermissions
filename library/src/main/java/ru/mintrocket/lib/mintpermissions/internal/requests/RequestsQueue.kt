package ru.mintrocket.lib.mintpermissions.internal.requests

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import ru.mintrocket.lib.mintpermissions.internal.models.Request

internal class RequestsQueue {

    private val _queue = MutableStateFlow<List<Request>>(emptyList())

    val headFlow = _queue.map { it.firstOrNull() }.distinctUntilChanged()

    fun add(request: Request) {
        _queue.update { it.plus(request) }
    }

    fun contains(request: Request): Boolean {
        return _queue.value.contains(request)
    }

    fun remove(request: Request) {
        _queue.update { it.minus(request) }
    }

    fun restore(requests: List<Request>) {
        _queue.value = requests.toList()
    }

    fun getAll(): List<Request> {
        return _queue.value.toList()
    }
}