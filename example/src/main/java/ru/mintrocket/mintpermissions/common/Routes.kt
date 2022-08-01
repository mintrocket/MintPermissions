package ru.mintrocket.mintpermissions.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import ru.mintrocket.mintpermissions.plain_flow.PlainFlowActivity
import ru.mintrocket.mintpermissions.data_flow.DataFlowActivity
import ru.mintrocket.mintpermissions.dialogs_flow.DialogsFlowActivity
import ru.mintrocket.mintpermissions.playground.PlaygroundActivity
import ru.mintrocket.mintpermissions.simple.SimpleExampleActivity

object Routes {

    fun dialogsFlow(context: Context) {
        context.startActivity(Intent(context, DialogsFlowActivity::class.java))
    }

    fun plainFlow(context: Context) {
        context.startActivity(Intent(context, PlainFlowActivity::class.java))
    }

    fun dataFlow(context: Context) {
        context.startActivity(Intent(context, DataFlowActivity::class.java))
    }

    fun playground(context: Context) {
        context.startActivity(Intent(context, PlaygroundActivity::class.java))
    }

    fun simple(context: Context) {
        context.startActivity(Intent(context, SimpleExampleActivity::class.java))
    }

    fun appSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}