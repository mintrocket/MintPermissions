package ru.mintrocket.mintpermissions.dialogs_flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.PermissionEvents
import ru.mintrocket.mintpermissions.common.PermissionsFormatter
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.common.onEachEventNotNull
import ru.mintrocket.mintpermissions.databinding.ActivityDialogsFlowBinding

class DialogsFlowActivity : AppCompatActivity(R.layout.activity_dialogs_flow) {

    private val binding by viewBinding<ActivityDialogsFlowBinding>()
    private val viewModel by viewModel<DialogsViewModel>()
    private val permissionsManager by inject<MintPermissionsManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionsManager.init(this)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btCamera.setOnClickListener {
            viewModel.onActionClick(false)
        }
        binding.llButtons.btNextActivity.setOnClickListener {
            Routes.dialogsFlow(this)
        }
        binding.llButtons.btAppSettings.setOnClickListener {
            Routes.appSettings(this)
        }
    }

    private fun initObservers() {
        viewModel.grantedEvent.onEachEventNotNull {
            PermissionEvents.grantedToast(this)
        }.launchIn(lifecycleScope)

        viewModel.deniedEvent.onEachEventNotNull {
            PermissionEvents.deniedDialog(this, it)
        }.launchIn(lifecycleScope)

        viewModel.rationaleEvent.onEachEventNotNull {
            PermissionEvents.rationaleDialog(this, it) {
                viewModel.onActionClick(true)
            }
        }.launchIn(lifecycleScope)

        viewModel.deniedPermanentlyEvent.onEachEventNotNull {
            PermissionEvents.deniedPermanentlyDialog(this, it)
        }.launchIn(lifecycleScope)
    }
}