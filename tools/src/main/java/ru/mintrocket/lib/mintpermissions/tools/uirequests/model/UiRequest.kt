package ru.mintrocket.lib.mintpermissions.tools.uirequests.model

import java.io.Serializable

data class UiRequest<T>(val key: String, val data: T) : Serializable