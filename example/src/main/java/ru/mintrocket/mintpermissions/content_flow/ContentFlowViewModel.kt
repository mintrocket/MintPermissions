package ru.mintrocket.mintpermissions.content_flow

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.flows.uirequests.SomeLib

class ContentFlowViewModel(
    private val permissionsController: MintPermissionsController
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

    private val permissionsFlow = SomeLib.createPlainFlow(cameraPermissions)

    val isAllGranted = permissionsFlow.isAllGrantedFlow

    val notGranted = permissionsFlow.firstNotGrantedFlow

    init {
        viewModelScope.launch {
            permissionsFlow.initialRequest()
        }
    }

    fun onActionClick() {
        viewModelScope.launch {
            permissionsFlow.request().also {
                Log.w("kekeke", "permissionsFlow $it")
            }
        }
    }
}