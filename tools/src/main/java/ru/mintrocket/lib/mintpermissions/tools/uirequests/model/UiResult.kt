package ru.mintrocket.lib.mintpermissions.tools.uirequests.model

import java.io.Serializable

data class UiResult<T, R>(val request: UiRequest<T>, val data: R) : Serializable