package ru.kudagonish.feature_clearing.ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.feature_clearing.ui.tab.content.ClearingTabContent

@Composable
internal fun ClearingTab(
    viewModel: ClearingTabViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ClearingTabContent(state, viewModel::sendEvent)
}