package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.feature_trash_bin.domain.PhotoListInteractor
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Action
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

internal class TrashBinViewModel(
    private val getTrashedPhotoUseCase: PhotoListInteractor
) : BaseViewModel<TrashBinScreenState, Event, Action>(TrashBinScreenState()) {

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnTabClick -> {
                updateState { it.copy(currentTab = event.tab) }
            }
        }
    }

    override fun resetState() {}

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData() {
        state.map { it.currentTab }
            .distinctUntilChanged()
            .flatMapLatest { tab ->
                when (tab) {
                    TabType.ToDeletion -> getTrashedPhotoUseCase.getDeletionPhoto()
                    TabType.TrashBin -> getTrashedPhotoUseCase.getTrashedPhoto()
                }
            }
            .onEach {
                val mappedImages = it.map { ImageUiModel(it.src) }.toImmutableList()
                updateState { it.copy(photos = mappedImages) }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    sealed interface Event : ViewModelEvent {
        data class OnTabClick(val tab: TabType) : Event
    }

    sealed interface Action : ViewModelAction
}