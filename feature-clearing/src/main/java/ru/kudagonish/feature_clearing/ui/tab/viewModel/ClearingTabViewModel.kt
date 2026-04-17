package ru.kudagonish.feature_clearing.ui.tab.viewModel

import android.content.IntentSender
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_clearing.domain.ClearingInformationInteractor
import ru.kudagonish.feature_clearing.domain.PhotoActionInteractor
import ru.kudagonish.feature_clearing.domain.PhotosOperationRequestInteractor
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Action
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Event
import kotlin.time.Clock

internal class ClearingTabViewModel(
    private val photosInteractor: ClearingInformationInteractor,
    private val photoActionInteractor: PhotoActionInteractor,
    private val crateOperationRequestUseCase: PhotosOperationRequestInteractor,
) : BaseViewModel<ClearingTabState, Event, Action>(ClearingTabState()) {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private val actionMutex = Mutex()
    private var deletionType: DeletionType? = null

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.DeletePhoto -> photoNegativeAction(event.image.src)
            is Event.KeepPhoto -> {
                photoPositiveAction(event.image.src)
            }
            is Event.OnNegativeActionApplied -> {
                onNegativeActionApplied(event.type)
                deletionType = event.type
            }
            Event.OnNegativeActionCompleted -> {
                deletionType?.let { type -> onNegativeActionCompleted(type) }
                deletionType = null
            }
            is Event.OnClickViewDeletionPhotos -> produceAction(Action.NavigateToBinDeletionTab)
        }
    }

    override fun resetState() {}

    override fun loadData() {
        photosInteractor.getPhotos(today)
            .onEach { list ->
                val mappedList = list.map { ImageUiModel(src = it.src) }.toImmutableList()
                updateState { it.copy(images = mappedList) }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        photosInteractor.getTrashedPhotoCount()
            .onEach { updateState { state -> state.copy(countToTrash = it) } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

        photosInteractor.getDeletionPhotoCount()
            .onEach { updateState { state -> state.copy(countToDelete = it) } }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    private fun photoPositiveAction(uri: String) {
        if(!actionMutex.tryLock()) return
        viewModelScope.launch(Dispatchers.IO) {
            photoActionInteractor.positive(uri)
            actionMutex.unlock()
        }
    }

    private fun photoNegativeAction(uri: String) {
        if(!actionMutex.tryLock()) return
        viewModelScope.launch(Dispatchers.IO) {
            photoActionInteractor.negative(uri)
            actionMutex.unlock()
        }
    }

    private fun onNegativeActionApplied(deleteType: DeletionType) {
        viewModelScope.launch(Dispatchers.IO) {
            val intent = crateOperationRequestUseCase(deleteType)
            produceAction(Action.RequestDeletePermission(intent.intentSender))
        }
    }

    private fun onNegativeActionCompleted(deleteType: DeletionType) {
        viewModelScope.launch(Dispatchers.IO) {
            crateOperationRequestUseCase.completeNegativeAction(deleteType,today)
        }
    }

    sealed interface Event : ViewModelEvent {
        data class KeepPhoto(val image: ImageUiModel) : Event
        data class DeletePhoto(val image: ImageUiModel) : Event
        data class OnNegativeActionApplied(val type: DeletionType) : Event
        data object OnNegativeActionCompleted : Event
        data object OnClickViewDeletionPhotos : Event
    }

    sealed interface Action : ViewModelAction {
        data class RequestDeletePermission(val intentSender: IntentSender) : Action
        data object NavigateToBinDeletionTab: Action
    }
}