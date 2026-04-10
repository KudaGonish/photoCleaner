package ru.kudagonish.feature_clearing.ui.tab

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.feature_clearing.domain.GetImagesUseCase
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel.Action
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel.Event
import kotlin.time.Clock

internal class ClearingTabViewModel(
    private val imagesUseCase: GetImagesUseCase,
) : BaseViewModel<ClearingTabState, Event, Action>(ClearingTabState()) {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.DeleteImage -> updateState {
                it.copy(images = it.images.filter { it != event.image }.toImmutableList())
            }
            is Event.SaveImage -> {
                updateState {
                    it.copy(images = it.images.filter { it != event.image }.toImmutableList())
                }
            }
        }
    }

    override fun resetState() {}

    override fun loadData() {
        imagesUseCase.invoke(today)
            .onEach { list ->
                val mappedList = list.map { Image(src = it.src) }.toImmutableList()
                updateState { it.copy(images = mappedList) }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    sealed interface Event : ViewModelEvent {
        data class SaveImage(val image: Image) : Event
        data class DeleteImage(val image: Image) : Event
    }

    sealed interface Action : ViewModelAction
}

internal data class ClearingTabState(
    val images: ImmutableList<Image> = persistentListOf()
)

internal data class Image(
    val src: String,
)