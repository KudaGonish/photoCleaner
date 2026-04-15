package ru.kudagonish.feature_trash_bin.ui.tab.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_trash_bin.ui.tab.ui.SingleChoiceSegmentedButtons
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TabType
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinScreenState
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TrashBinViewModel.Event

@Composable
internal fun TrashBinTabContent(
    state: TrashBinScreenState,
    sendEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        SingleChoiceSegmentedButtons(
            tabState = state.currentTab,
            onTabClick = { sendEvent(Event.OnTabClick(it)) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrashBinTabContentSystemTrashPreview() {
    PhotoCleanerTheme {
        TrashBinTabContent(
            state = TrashBinScreenState(currentTab = TabType.TrashBin),
            sendEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrashBinTabContentInstantPreview() {
    PhotoCleanerTheme {
        TrashBinTabContent(
            state = TrashBinScreenState(currentTab = TabType.ToDeletion),
            sendEvent = {}
        )
    }
}