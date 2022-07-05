package ru.mintrocket.lib.mintpermissions.tools.uirequests.internal

import kotlinx.coroutines.flow.*

internal class FlowQueue<T> {

    private val _queue = MutableStateFlow<List<T>>(emptyList())

    val queueFlow = _queue.asStateFlow()

    val headFlow = _queue.map { it.firstOrNull() }.distinctUntilChanged()

    fun add(item: T) {
        _queue.update { it.plus(item) }
    }

    fun contains(item: T): Boolean {
        return _queue.value.contains(item)
    }

    fun remove(item: T) {
        _queue.update { it.minus(item) }
    }

    fun restore(items: List<T>) {
        _queue.value = items.toList()
    }
}