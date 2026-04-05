package ru.kudagonish.feature_settings.ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.feature_settings.ui.tab.content.SettingsTabContent
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel

@Composable
internal fun SettingsTab(viewModel: SettingsTabViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.action.collect { action ->
            when (action) {
                else -> Unit
            }
        }
    }

    SettingsTabContent(state, viewModel::sendEvent)
}