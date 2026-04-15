package ru.kudagonish.feature_clearing.ui.tab.viewModel

import android.content.IntentSender
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.kudagonish.core_ui.viewModel.BaseViewModel
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

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.DeletePhoto -> photoNegativeAction(event.image.src)
            is Event.KeepPhoto -> photoPositiveAction(event.image.src)

            //TODO отправляется когда мы сверху тыкаем "удалить"
            is Event.OnDeleteConfirmed -> viewModelScope.launch(Dispatchers.IO) {
                //pendingDeleteImage?.let { deleteImageUseCase.invoke(it.src, today) }
//                pendingDeleteImage = null
            }

            //TODO тут по идее если мы не согласились, то хз че делать, но не то что чичас написано
            is Event.OnDeleteCanceled -> {
                /*pendingDeleteImage?.let { image ->
                    updateState { state ->
                        val newList = state.images.filter { it.src != image.src }.toMutableList()
                            .apply { this.add(image) }
                        state.copy(images = newList.toImmutableList())
                    }
                }
                pendingDeleteImage = null*/
            }
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
        viewModelScope.launch(Dispatchers.IO) {
            photoActionInteractor.positive(uri)
        }
    }

    private fun photoNegativeAction(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            photoActionInteractor.negative(uri, today)
        }
    }

    sealed interface Event : ViewModelEvent {
        data class KeepPhoto(val image: ImageUiModel) : Event
        data class DeletePhoto(val image: ImageUiModel) : Event
        data object OnDeleteConfirmed : Event
        data object OnDeleteCanceled : Event
    }

    sealed interface Action : ViewModelAction {
        data class RequestDeletePermission(val intentSender: IntentSender) : Action
    }
}