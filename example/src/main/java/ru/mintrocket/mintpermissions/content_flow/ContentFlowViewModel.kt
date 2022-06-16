package ru.mintrocket.mintpermissions.content_flow

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mintrocket.lib.mintpermissions.MintPermissionsController
import ru.mintrocket.lib.mintpermissions.ext.filterDenied
import ru.mintrocket.lib.mintpermissions.ext.filterNotGranted
import ru.mintrocket.lib.mintpermissions.ext.isAllGranted
import ru.mintrocket.lib.mintpermissions.models.MintPermission
import ru.mintrocket.mintpermissions.common.Event

class ContentFlowViewModel(
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

    val grantedEvent = _grantedEvent.asStateFlow()
    val deniedEvent = _deniedEvent.asStateFlow()

    val notGrantedState = permissionsController
        .observe(cameraPermissions)
        .map { it.filterNotGranted().firstOrNull() }
        .distinctUntilChanged()

    init {
        tryRunAction()
    }

    fun onActionClick() {
        tryRunAction()
    }

    private fun tryRunAction() {
        viewModelScope.launch {
            val result = permissionsController.request(cameraPermissions)
            if (result.results.isAllGranted()) {
                _grantedEvent.value = Event(Unit)
                return@launch
            }
            val denied = result.results.filterDenied()
            if (denied.isNotEmpty()) {
                _deniedEvent.value = Event(denied.map { it.status.permission })
                return@launch
            }
        }
    }
}