package ru.kudagonish.core_ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel.ViewModelAction
import ru.kudagonish.core_ui.viewModel.BaseViewModel.ViewModelEvent

abstract class BaseViewModel<State, Event : ViewModelEvent, Action : ViewModelAction>(
    initialState: State
) : ViewModel() {

    private val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = mutableState.asStateFlow()

    private val _actionFlow: MutableSharedFlow<Action> = MutableSharedFlow()
    val action = _actionFlow.asSharedFlow()

    fun sendEvent(event: Event) = handleEvent(event)

    protected abstract fun handleEvent(event: Event)

    protected abstract fun resetState()

    protected abstract fun loadData()

    protected fun updateState(onUpdate: (State) -> State) {
        mutableState.update { onUpdate(state.value) }
    }

    protected fun produceAction(action: Action) {
        viewModelScope.launch { _actionFlow.emit(action) }
    }

    interface ViewModelAction
    interface ViewModelEvent
}