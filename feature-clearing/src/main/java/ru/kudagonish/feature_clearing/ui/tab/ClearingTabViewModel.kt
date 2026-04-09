package ru.kudagonish.feature_clearing.ui.tab

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel.Action
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel.Event
import kotlin.time.Clock

internal class ClearingTabViewModel : BaseViewModel<ImmutableList<>, Event, Action>() {

    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    override fun handleEvent(event: Event) {
        when (event) {

        }
    }

    override fun resetState() {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        viewModelScope.launch {

        }
    }



    sealed interface Event : BaseViewModel.ViewModelEvent {
        data class SaveImage(val image: Any) : Event
        data class DeleteImage(val image: Any) : Event
    }

    sealed interface Action : BaseViewModel.ViewModelAction
}