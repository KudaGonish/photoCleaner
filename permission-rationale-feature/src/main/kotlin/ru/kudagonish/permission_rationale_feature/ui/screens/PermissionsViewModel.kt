package ru.kudagonish.permission_rationale_feature.ui.screens

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.kudagonish.core.ContextProvider
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.permission_rationale_feature.domain.PermissionRepository
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel.Action
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel.Event
import ru.kudagonish.photofinder.domain.useCase.IScanGalleryUseCase

internal class PermissionsViewModel(
    private val repository: PermissionRepository,
    private val scanGalleryUseCase: IScanGalleryUseCase,
) : BaseViewModel<Any, Event, Action>(Any()) {

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnNavigateToPhoneSettings -> produceAction(Action.NavigateToPhoneSettings)
            Event.OnFailureRequestPermission -> incrementRequestCount()
            Event.OnSuccessRequestPermission -> onSuccessfulPermissionRequest()
            Event.OnNavigateToMainScreen -> {
                viewModelScope.launch(Dispatchers.IO) {
                    resetRequestCount()
                    produceAction(Action.NavigateToMainScreen)
                }
            }
        }
    }

    override fun resetState() = Unit
    override fun loadData() = Unit

    private fun onSuccessfulPermissionRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            scanGalleryUseCase.invoke()
            resetRequestCount()
        }
    }

    private fun incrementRequestCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentCount = repository.getRequestPermissionCount().first()
            repository.updateRequestCount(currentCount + 1)
        }
    }

    private fun resetRequestCount() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.resetRequestCount()
        }
    }

    sealed interface Event : ViewModelEvent {
        data object OnFailureRequestPermission : Event
        data object OnSuccessRequestPermission : Event
        data object OnNavigateToMainScreen : Event
        data object OnNavigateToPhoneSettings : Event
    }

    sealed interface Action : ViewModelAction {
        data object NavigateToMainScreen : Action
        data object NavigateToPhoneSettings : Action
    }
}