package ru.mintrocket.mintpermissions.data_flow

data class DataFlowScreenState(
    val shops: List<String>? = null,
    val error: Throwable? = null,
    val loading: Boolean = false
)
