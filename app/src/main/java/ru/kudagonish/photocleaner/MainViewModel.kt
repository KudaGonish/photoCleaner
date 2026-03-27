package ru.kudagonish.photocleaner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.kudagonish.permission_rationale_feature.domain.PermissionRepository

internal class MainViewModel(
    permissionRepository: PermissionRepository,
) : ViewModel() {

    val requestPermissionCount: StateFlow<Int?> = permissionRepository
        .getRequestPermissionCount()
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}
