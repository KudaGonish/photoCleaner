package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Action
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

internal class TrashBinViewModel : BaseViewModel<TrashBinScreenState, Event, Action>(TrashBinScreenState()) {

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnTabClick -> {
                updateState { it.copy(currentTab = event.tab) }
            }
        }
    }

    override fun resetState() {}

    override fun loadData() {}

    sealed interface Event : ViewModelEvent {
        data class OnTabClick(val tab: TabType) : Event
    }

    sealed interface Action : ViewModelAction
}