package ru.mintrocket.mintpermissions.content_flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mintrocket.lib.mintpermissions.models.MintPermissionStatus
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.databinding.ActivityContentFlowBinding

class ContentFlowActivity : AppCompatActivity(R.layout.activity_content_flow) {

    private val binding by viewBinding<ActivityContentFlowBinding>()
    private val viewModel by viewModel<ContentFlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        binding.btAction.setOnClickListener {
            viewModel.onActionClick()
        }
        binding.llButtons.btNextActivity.setOnClickListener {
            Routes.contentFlow(this)
        }
        binding.llButtons.btAppSettings.setOnClickListener {
            Routes.appSettings(this)
        }
    }

    private fun initObservers() {
        viewModel.notGranted
            .onEach {
                binding.llInfo.isVisible = it != null
                binding.btAction.text = if (it == null) {
                    "Take a video"
                } else {
                    "Give permission"
                }
            }
            .launchIn(lifecycleScope)

        viewModel.notGranted
            .filterNotNull()
            .onEach { status ->
                val icon = when (status) {
                    is MintPermissionStatus.NeedsRationale -> R.drawable.ic_outline_info_24
                    else -> null
                }?.let { ContextCompat.getDrawable(this, it) }
                val message = when (status) {
                    is MintPermissionStatus.Denied -> "Denied message for permission ${status.permission}"
                    is MintPermissionStatus.NeedsRationale -> "Rationale with description why app needs this permission: ${status.permission}"
                    else -> null
                }
                binding.ivPermissionImage.isVisible = icon != null
                binding.tvPermissionMessage.isVisible = message != null
                binding.ivPermissionImage.setImageDrawable(icon)
                binding.tvPermissionMessage.text = message
            }
            .launchIn(lifecycleScope)
    }
}