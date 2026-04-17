package ru.kudagonish.feature_clearing.ui.tab

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest.Builder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.feature_clearing.ui.tab.content.ClearingTabContent
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Action

@Composable
internal fun ClearingTab(
    viewModel: ClearingTabViewModel = koinViewModel(),
    onNavigateToBinDeletionTab: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val intentSenderLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.sendEvent(ClearingTabViewModel.Event.OnNegativeActionCompleted)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.action.collectLatest { action ->
            when (action) {
                Action.NavigateToBinDeletionTab -> onNavigateToBinDeletionTab()
                is Action.RequestDeletePermission -> {
                    val request = Builder(action.intentSender).build()
                    intentSenderLauncher.launch(request)
                }
            }
        }
    }

    ClearingTabContent(state, viewModel::sendEvent)
}