package ru.mintrocket.lib.mintpermissions.tools.initializer

import androidx.activity.ComponentActivity

fun interface ManagerInitializer {

    fun init(activity: ComponentActivity)
}