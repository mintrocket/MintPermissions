package ru.mintrocket.mintpermissions.data_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataFlowViewModel(
    private val repository: DataFlowRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(DataFlowScreenState())
    val screenState = _screenState.asStateFlow()

    fun onActionClick() {
        viewModelScope.launch {
            _screenState.update { it.copy(loading = true) }
            runCatching {
                repository.searchNearestShops()
            }.onSuccess { shops ->
                _screenState.update { it.copy(shops = shops, error = null) }
            }.onFailure { throwable ->
                _screenState.update { it.copy(shops = null, error = throwable) }
            }
            _screenState.update { it.copy(loading = false) }
        }
    }
}