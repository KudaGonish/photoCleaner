package ru.kudagonish.feature_trash_bin.ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.feature_trash_bin.ui.tab.content.TrashBinTabContent
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel

@Composable
internal fun TrashBinTab(
    viewModel: TrashBinViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TrashBinTabContent(state, viewModel::sendEvent)
}