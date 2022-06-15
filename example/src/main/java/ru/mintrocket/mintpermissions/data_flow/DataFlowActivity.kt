package ru.mintrocket.mintpermissions.data_flow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mintrocket.lib.mintpermissions.MintPermissionsManager
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.databinding.ActivityDataFlowBinding

class DataFlowActivity : AppCompatActivity(R.layout.activity_data_flow) {

    private val binding by viewBinding<ActivityDataFlowBinding>()
    private val viewModel by viewModel<DataFlowViewModel>()
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
        binding.btAction.setOnClickListener {
            viewModel.onActionClick()
        }
        binding.llButtons.btNextActivity.setOnClickListener {
            Routes.dataFlow(this)
        }
        binding.llButtons.btAppSettings.setOnClickListener {
            Routes.appSettings(this)
        }
    }

    private fun initObservers() {
        viewModel.screenState
            .map { it.loading }
            .distinctUntilChanged()
            .onEach {
                binding.btAction.isInvisible = it
                binding.pbLoading.isVisible = it
            }
            .launchIn(lifecycleScope)

        viewModel.screenState
            .map { it.shops }
            .distinctUntilChanged()
            .onEach { shops ->
                binding.tvShops.isVisible = shops != null
                binding.tvShops.text = shops?.joinToString("\n")
            }
            .launchIn(lifecycleScope)

        viewModel.screenState
            .map { it.error }
            .distinctUntilChanged()
            .onEach { error ->
                binding.tvError.isVisible = error != null
                binding.tvError.text = error?.message.toString()
            }
            .launchIn(lifecycleScope)
    }
}