package ru.kudagonish.feature_trash_bin.ui.tab

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.core_ui.viewModel.MainPagerState
import ru.kudagonish.feature_trash_bin.ui.tab.content.TrashBinTabContent
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TabType
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

@Composable
internal fun TrashBinTab(
    viewModel: TrashBinViewModel = koinViewModel(),
    mainPagerState: MainPagerState
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        mainPagerState.targetTrashTab.collect {
            Log.d("TAG", "TrashBinTab: $it")
            val tab = if (it == 0) TabType.TrashBin else TabType.ToDeletion
            viewModel.sendEvent(Event.OnTabClick(tab))
        }
    }

    TrashBinTabContent(state, viewModel::sendEvent)
}