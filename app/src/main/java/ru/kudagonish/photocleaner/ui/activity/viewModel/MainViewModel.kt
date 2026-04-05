package ru.kudagonish.photocleaner.ui.activity.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.photocleaner.domain.AppRepository
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel.Action
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel.Event

internal class MainViewModel(
    private val appRepository: AppRepository,
) : BaseViewModel<MainScreenState, Event, Action>(MainScreenState()) {

    init {
        loadData()
    }

    val requestPermissionCount: StateFlow<Int?> = appRepository
        .getRequestPermissionCount()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnSetApplicationLanguage -> setCurrentLocale(event.locale)
        }
    }

    override fun resetState() = Unit

    override fun loadData() {
        viewModelScope.launch {
            appRepository.languageFlow.collect {
                updateState { state -> state.copy(language = it) }
            }
        }
        viewModelScope.launch {
            appRepository.themeFlow.collect {
                updateState { state ->
                    val theme = when (it) {
                        AppTheme.Dark -> true
                        AppTheme.Light -> false
                        AppTheme.System -> null
                    }
                    state.copy(theme = theme)
                }
            }
        }
    }

    private fun setCurrentLocale(locale: String) {
        viewModelScope.launch {
            appRepository.setLocale(locale)
        }
    }

    sealed interface Event : ViewModelEvent {
        data class OnSetApplicationLanguage(val locale: String) : Event
    }

    sealed interface Action : ViewModelAction
}