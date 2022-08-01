package ru.mintrocket.mintpermissions.content_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.flows.MintPermissionsFlow

class ContentFlowViewModel : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }

    private val permissionsFlow = MintPermissionsFlow.createPlainFlow(cameraPermissions)

    val notGranted = permissionsFlow.firstNotGrantedFlow

    fun onActionClick() {
        viewModelScope.launch {
            permissionsFlow.requestSequentially()
        }
    }
}