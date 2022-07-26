package ru.mintrocket.mintpermissions.dialogs_flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.launchIn
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mintrocket.lib.mintpermissions.flows.uirequests.MintPermissionsFlowZygote
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.PermissionEvents
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.common.onEachEventNotNull
import ru.mintrocket.mintpermissions.databinding.ActivityDialogsFlowBinding

class DialogsFlowActivity : AppCompatActivity(R.layout.activity_dialogs_flow) {

    private val binding by viewBinding<ActivityDialogsFlowBinding>()
    private val viewModel by viewModel<DialogsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MintPermissionsFlowZygote.createManager().init(this)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btCamera.setOnClickListener {
            viewModel.onActionClick()
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
    }
}