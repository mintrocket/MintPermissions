package ru.mintrocket.lib.mintpermissions.tools.uirequests

import androidx.activity.ComponentActivity
import ru.mintrocket.lib.mintpermissions.tools.uirequests.models.UiRequest

public interface UiRequestConsumer<T, R> {

    public suspend fun request(activity: ComponentActivity, request: UiRequest<T>): R
}