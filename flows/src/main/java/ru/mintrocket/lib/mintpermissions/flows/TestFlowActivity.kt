package ru.mintrocket.lib.mintpermissions.flows

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import ru.mintrocket.lib.mintpermissions.flows.uirequests.SomeLib

class TestFlowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SomeLib.createManager().init(this)
        setContentView(R.layout.activity_test_flow)

        val flow = SomeLib.controller

        findViewById<View>(R.id.button).setOnClickListener {
            lifecycleScope.launch {
                Log.w("kekeke","launch start")
                withTimeout(10000){
                    flow.request(
                        listOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                        )
                    )
                }
                Log.w("kekeke","launch end")
            }.invokeOnCompletion {
                Log.w("kekeke","invokeOnCompletion $it")
            }
        }
    }
}