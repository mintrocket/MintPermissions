package ru.mintrocket.lib.mintpermissions.tools.uirequests.models

import java.io.Serializable

data class UiResult<T, R>(val request: UiRequest<T>, val data: R) : Serializable