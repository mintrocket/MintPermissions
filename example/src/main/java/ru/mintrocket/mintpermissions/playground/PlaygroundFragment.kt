package ru.mintrocket.mintpermissions.playground

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.mintrocket.mintpermissions.R
import ru.mintrocket.mintpermissions.common.PermissionsFormatter
import ru.mintrocket.mintpermissions.common.Routes
import ru.mintrocket.mintpermissions.databinding.FragmentPlaygroundBinding

class PlaygroundFragment : Fragment(R.layout.fragment_playground) {

    private val binding by viewBinding<FragmentPlaygroundBinding>()
    private val viewModel by viewModel<PlaygroundViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }
        binding.btAll.setOnClickListener { viewModel.requestAll() }
        binding.btAllQueue.setOnClickListener { viewModel.requestAllQueue() }
        binding.btCamera.setOnClickListener { viewModel.requestCamera() }
        binding.btLocation.setOnClickListener { viewModel.requestLocation() }
        binding.btStorage.setOnClickListener { viewModel.requestStorage() }
        binding.llButtons.btNextActivity.setOnClickListener {
            startActivity(Intent(requireContext(), PlaygroundActivity::class.java))
        }
        binding.llButtons.btAppSettings.setOnClickListener {
            Routes.appSettings(requireContext())
        }
    }

    private fun initObservers() {
        viewModel.targetPermissionsFlow
            .map { PermissionsFormatter.formatStatuses(it) }
            .flowOn(Dispatchers.Default)
            .onEach { binding.tvTargetPermissions.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.latestResult
            .map { PermissionsFormatter.formatResults(it) }
            .flowOn(Dispatchers.Default)
            .onEach { binding.tvLatestPermissions.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.allAppPermissionsFlow
            .map { PermissionsFormatter.formatStatuses(it) }
            .flowOn(Dispatchers.Default)
            .onEach { binding.tvAllPermissions.text = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}