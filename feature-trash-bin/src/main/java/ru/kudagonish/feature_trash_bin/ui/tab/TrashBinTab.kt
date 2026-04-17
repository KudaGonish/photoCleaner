package ru.kudagonish.feature_trash_bin.ui.tab

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest.Builder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.core_ui.viewModel.MainPagerState
import ru.kudagonish.feature_trash_bin.ui.tab.content.TrashBinTabContent
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TabType
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Action
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

@Composable
internal fun TrashBinTab(
    viewModel: TrashBinViewModel = koinViewModel(),
    mainPagerState: MainPagerState
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val intentSenderLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.sendEvent(Event.OnDeleteCompleted)
        }
    }

    LaunchedEffect(Unit) {
        launch {
            mainPagerState.targetTrashTab.collect {
                val tab = if (it == 0) TabType.TrashBin else TabType.ToDeletion
                viewModel.sendEvent(Event.OnTabClick(tab))
            }
        }
        viewModel.action.collectLatest { action ->
            when(action){
                is Action.RequestDeletePermission -> {
                    val request = Builder(action.intentSender).build()
                    intentSenderLauncher.launch(request)
                }
            }
        }
    }

    TrashBinTabContent(state, viewModel::sendEvent)
}