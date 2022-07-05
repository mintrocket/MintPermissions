package ru.mintrocket.lib.mintpermissions.tools.uirequests

interface UiRequestController<T, R> {
    suspend fun request(requestData: T): R
}