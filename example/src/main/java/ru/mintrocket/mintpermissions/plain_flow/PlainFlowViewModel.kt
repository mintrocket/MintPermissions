package ru.mintrocket.mintpermissions.plain_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlow
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsPlainFlow

class PlainFlowViewModel(
    private val permissionsFlow: MintPermissionsPlainFlow
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }

    val notGranted = permissionsFlow.observeFirstNotGranted()

    fun onActionClick() {
        viewModelScope.launch {
            permissionsFlow.requestSequentially()
        }
    }
}