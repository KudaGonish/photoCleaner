package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.viewModel.BaseViewModel
import ru.kudagonish.datastore.settings.DataStoreSettings
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Action
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Event

internal class SettingsTabViewModel(
    private val dataStoreSettings: DataStoreSettings
) : BaseViewModel<SettingsTabState, Event, Action>(SettingsTabState()) {

    init {
        loadData()
    }

    override fun handleEvent(event: Event) {
        when(event){
            else -> Unit
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

    sealed interface Event : ViewModelEvent
    sealed interface Action : ViewModelAction
}