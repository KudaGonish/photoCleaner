package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import android.content.IntentSender
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
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_trash_bin.domain.PhotoActionInteractor
import ru.kudagonish.feature_trash_bin.domain.PhotoListInteractor
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Action
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

internal class TrashBinViewModel(
    private val photoListInteraction: PhotoListInteractor,
    private val photoActionInteraction: PhotoActionInteractor
) : BaseViewModel<TrashBinScreenState, Event, Action>(TrashBinScreenState()) {

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            Event.OnDeleteAllClick -> onDeleteAllPhotos()
            is Event.OnTabClick -> updateState { it.copy(currentTab = event.tab) }
            Event.OnDeleteCompleted -> onDeleteComplete()
        }
    }

    override fun resetState() {}

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun loadData() {
        state.map { it.currentTab }
            .distinctUntilChanged()
            .flatMapLatest { tab ->
                when (tab) {
                    TabType.ToDeletion -> photoListInteraction.getDeletionPhoto()
                    TabType.TrashBin -> photoListInteraction.getTrashedPhoto()
                }
            }
            .onEach {
                val mappedImages = it.map { ImageUiModel(it.src) }.toImmutableList()
                updateState { it.copy(photos = mappedImages) }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun onDeleteAllPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val type = state.value.currentTab.mapToDeletionType()
            val pendingIntent = photoActionInteraction.deleteAllPhotos(type)
            produceAction(Action.RequestDeletePermission(pendingIntent.intentSender))
        }
    }

    private fun onDeleteComplete(){
        viewModelScope.launch(Dispatchers.IO) {
            val type = state.value.currentTab.mapToDeletionType()
            photoActionInteraction.completePhotoDeletion(type)
        }
    }

    private fun TabType.mapToDeletionType() = when (this) {
        TabType.ToDeletion -> DeletionType.Instant
        TabType.TrashBin -> DeletionType.SystemTrash
    }

    sealed interface Event : ViewModelEvent {
        data class OnTabClick(val tab: TabType) : Event
        data object OnDeleteAllClick : Event
        data object OnDeleteCompleted : Event
    }

    sealed interface Action : ViewModelAction {
        data class RequestDeletePermission(val intentSender: IntentSender) : Action
    }
}