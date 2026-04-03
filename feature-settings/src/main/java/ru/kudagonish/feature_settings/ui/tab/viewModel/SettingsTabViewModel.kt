package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.datastore.settings.DataStoreSettings
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Action
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Event

internal class SettingsTabViewModel(
    private val dataStoreSettings: DataStoreSettings
) : BaseViewModel<SettingsTabState, Event, Action>(SettingsTabState()) {

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnChangeLanguage -> changeLanguage(event.value)
            is Event.OnChangeTheme -> changeTheme(event.value)
            is Event.OnChangeAlgorithm -> changeAlgorithm(event.value)
        }
    }

    override fun resetState() = Unit

    override fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreSettings.settingsFlow.collect { settings ->
                updateState { currentState ->
                    with(settings) {
                        currentState.copy(
                            themes = theme.mapThemes(),
                            languages = language.mapLanguages(),
                            algorithms = algorithm.mapAlgorithms(),
                            deletionTypes = deletionType.mapDeletionTypes()
                        )
                    }
                }
            }
        }
    }

    private fun changeLanguage(value: Language) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreSettings.changeLanguage(value)
        }
    }

    private fun changeTheme(value: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreSettings.changeTheme(value)
        }
    }

    private fun changeAlgorithm(value: WorkAlgorithm) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreSettings.changAlgorithm(value)
        }
    }

    sealed interface Event : ViewModelEvent {
        data class OnChangeLanguage(val value: Language) : Event
        data class OnChangeTheme(val value: AppTheme) : Event
        data class OnChangeAlgorithm(val value: WorkAlgorithm) : Event
    }

    sealed interface Action : ViewModelAction
}