package ru.mintrocket.mintpermissions.simple

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsInitializer
import ru.mintrocket.lib.mintpermissions.models.MintPermissionResult
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.PermissionEvents
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.databinding.ActivitySimpleExampleBinding

class SimpleExampleActivity : AppCompatActivity(R.layout.activity_simple_example) {

    private val binding by viewBinding<ActivitySimpleExampleBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MintPermissionsInitializer.createManager().init(this)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btCamera.setOnClickListener {
            takePhoto()
        }
        binding.llButtons.btNextActivity.setOnClickListener {
            Routes.simple(this)
        }
        binding.llButtons.btAppSettings.setOnClickListener {
            Routes.appSettings(this)
        }
    }

    private fun takePhoto() {
        val controller = MintPermissionsInitializer.permissionsController
        lifecycleScope.launch {
            val result = controller.request(Manifest.permission.CAMERA)
            when (result.status) {
                is MintPermissionStatus.Granted -> {
                    PermissionEvents.grantedToast(this@SimpleExampleActivity)
                }
                is MintPermissionStatus.Denied,
                is MintPermissionStatus.NeedsRationale,
                is MintPermissionStatus.NotFound -> {
                    onAnotherResult(result)
                }
            }
        }
    }

    private fun onAnotherResult(result: MintPermissionResult) {
        val resultText = "Result = ${result.status::class.simpleName}"
        val actionText = "Needs action = ${result.action?.javaClass?.simpleName}"
        val text = "$resultText\n$actionText"
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}