package ru.kudagonish.permission_rationale.screens.permissions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kudagonish.permission_rationale.domain.PermissionRepository

internal class PermissionRationaleViewModel(
    private val repository: PermissionRepository
) : ViewModel() {

    fun incrementRequestCount() {
        viewModelScope.launch {
            val currentCount = repository.getRequestPermissionCount().first()
            repository.updateRequestCount(currentCount + 1)
        }
    }

    fun resetRequestCount() {
        viewModelScope.launch {
            repository.resetRequestCount()
        }
    }
}
