package ru.kudagonish.permission_rationale.screens.permissions

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.permission_rationale.domain.PermissionRepository
import ru.kudagonish.permission_rationale.screens.permissions.PermissionRationaleViewModel.Action
import ru.kudagonish.permission_rationale.screens.permissions.PermissionRationaleViewModel.Event

internal class PermissionRationaleViewModel(
    private val repository: PermissionRepository
) : BaseViewModel<Any, Event, Action>(Any()) {

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnFailureRequestPermission -> incrementRequestCount()
            Event.OnSuccessRequestPermission -> resetRequestCount()
        }
    }

    override fun resetState() = Unit
    override fun loadData() = Unit

    fun incrementRequestCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentCount = repository.getRequestPermissionCount().first()
            repository.updateRequestCount(currentCount + 1)
        }
    }

    fun resetRequestCount() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.resetRequestCount()
        }
    }

    sealed interface Event : ViewModelEvent {
        object OnFailureRequestPermission : Event
        object OnSuccessRequestPermission : Event
    }

    sealed interface Action : ViewModelAction
}
