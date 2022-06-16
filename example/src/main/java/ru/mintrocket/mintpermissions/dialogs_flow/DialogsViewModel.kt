package ru.mintrocket.mintpermissions.dialogs_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNeedsRationale
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.lib.mintpermissions.models.MintPermissionAction
import ru.mintrocket.mintpermissions.common.Event

class DialogsViewModel(
    private val permissionsController: MintPermissionsController
) : ViewModel() {

    companion object {
        private val cameraPermissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }

    private val _grantedEvent = MutableStateFlow<Event<Unit>?>(null)
    private val _deniedEvent = MutableStateFlow<Event<List<MintPermission>>?>(null)
    private val _rationaleEvent = MutableStateFlow<Event<List<MintPermission>>?>(null)
    private val _deniedPermanentlyEvent = MutableStateFlow<Event<List<MintPermission>>?>(null)

    val grantedEvent = _grantedEvent.asStateFlow()
    val deniedEvent = _deniedEvent.asStateFlow()
    val rationaleEvent = _rationaleEvent.asStateFlow()
    val deniedPermanentlyEvent = _deniedPermanentlyEvent.asStateFlow()

    fun onActionClick(forceRequest: Boolean) {
        if (forceRequest) {
            runRequest()
        } else {
            runWithCheck()
        }
    }

    private fun runWithCheck() {
        viewModelScope.launch {
            val checkResult = permissionsController.get(cameraPermissions)
            if (checkResult.isAllGranted()) {
                _grantedEvent.value = Event(Unit)
                return@launch
            }
            val rationale = checkResult.filterNeedsRationale()
            if (rationale.isNotEmpty()) {
                _rationaleEvent.value = Event(rationale.map { it.permission })
                return@launch
            }
            runRequest()
        }
    }

    private fun runRequest() {
        viewModelScope.launch {
            val result = permissionsController.request(cameraPermissions)

            if (result.isAllGranted()) {
                _grantedEvent.value = Event(Unit)
                return@launch
            }

            val rationale = result.filter {
                it.action is MintPermissionAction.NeedsRationale
            }
            if (rationale.isNotEmpty()) {
                _rationaleEvent.value = Event(rationale.map { it.status.permission })
                return@launch
            }

            val deniedPermanently = result.filter {
                it.action is MintPermissionAction.DeniedPermanently
            }
            if (deniedPermanently.isNotEmpty()) {
                _deniedPermanentlyEvent.value =
                    Event(deniedPermanently.map { it.status.permission })
                return@launch
            }
            val denied = result.filterDenied()
            if (denied.isNotEmpty()) {
                _deniedEvent.value = Event(denied.map { it.status.permission })
                return@launch
            }
        }
    }
}