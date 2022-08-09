package ru.mintrocket.lib.mintpermissions.tools.uirequests

public interface UiRequestController<T, R> {
    public suspend fun request(requestData: T): R
}