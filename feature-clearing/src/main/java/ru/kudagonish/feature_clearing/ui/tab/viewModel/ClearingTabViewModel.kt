package ru.kudagonish.feature_clearing.ui.tab.viewModel

import android.content.IntentSender
import android.util.Log
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
import ru.kudagonish.feature_clearing.domain.DeleteImageUseCase
import ru.kudagonish.feature_clearing.domain.GetImagesUseCase
import ru.kudagonish.feature_clearing.domain.KeepImageUseCase
import ru.kudagonish.feature_clearing.domain.PhotosOperationRequestInteractor
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Action
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Event
import kotlin.time.Clock

internal class ClearingTabViewModel(
    private val imagesUseCase: GetImagesUseCase,
    private val keepImageUseCase: KeepImageUseCase,
    private val crateOperationRequestUseCase: PhotosOperationRequestInteractor,
    private val deleteImageUseCase: DeleteImageUseCase,
) : BaseViewModel<ClearingTabState, Event, Action>(ClearingTabState()) {

    private val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    private var pendingDeleteImage: ImageUiModel? = null

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.DeleteImage -> viewModelScope.launch(Dispatchers.IO) {
                pendingDeleteImage = event.image
                val pendingIntent = crateOperationRequestUseCase(listOf(event.image.src))
                produceAction(Action.RequestDeletePermission(pendingIntent.intentSender))
            }

            is Event.KeepImage -> viewModelScope.launch(Dispatchers.IO) {
                keepImageUseCase.invoke(event.image.src)
            }

            is Event.OnDeleteConfirmed -> viewModelScope.launch(Dispatchers.IO) {
                pendingDeleteImage?.let { deleteImageUseCase.invoke(it.src,today) }
                pendingDeleteImage = null
            }

            is Event.OnDeleteCanceled -> {
                pendingDeleteImage?.let { image ->
                    updateState { state ->
                        val newList = state.images.filter { it.src != image.src }.toMutableList()
                            .apply { this.add(image) }
                        state.copy(images = newList.toImmutableList())
                    }
                }
                pendingDeleteImage = null
            }
        }
    }

    override fun resetState() {}

    override fun loadData() {
        imagesUseCase.invoke(today)
            .onEach { list ->
                val mappedList = list.map { ImageUiModel(src = it.src) }.toImmutableList()
                updateState { it.copy(images = mappedList) }
                Log.d("TAG", "loadData: ${mappedList.size}")
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    sealed interface Event : ViewModelEvent {
        data class KeepImage(val image: ImageUiModel) : Event
        data class DeleteImage(val image: ImageUiModel) : Event
        data object OnDeleteConfirmed : Event
        data object OnDeleteCanceled : Event
    }

    sealed interface Action : ViewModelAction {
        data class RequestDeletePermission(val intentSender: IntentSender) : Action
    }
}