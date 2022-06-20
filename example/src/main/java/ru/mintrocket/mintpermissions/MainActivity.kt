package ru.mintrocket.mintpermissions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.mintrocket.lib.mintpermissions.flows.TestFlowActivity
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding.btDialogs.setOnClickListener {
            Routes.dialogsFlow(this)
        }

        binding.btContent.setOnClickListener {
            Routes.contentFlow(this)
        }

        binding.btData.setOnClickListener {
            Routes.dataFlow(this)
        }

        binding.btPlayground.setOnClickListener {
            Routes.playground(this)
        }

        binding.btSimple.setOnClickListener {
            //Routes.simple(this)
            startActivity(Intent(this, TestFlowActivity::class.java))
        }

        binding.btAppSettings.setOnClickListener {
            Routes.appSettings(this)
        }
    }
}