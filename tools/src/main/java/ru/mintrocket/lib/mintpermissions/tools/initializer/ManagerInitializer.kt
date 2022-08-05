package ru.mintrocket.lib.mintpermissions.tools.initializer

import androidx.activity.ComponentActivity

public fun interface ManagerInitializer {

    public fun init(activity: ComponentActivity)
}