package ru.kudagonish.feature_trash_bin.ui.tab.content

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ru.kudagonish.core_ui.elements.bottomMenuPadding
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

        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 4.dp,
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = bottomMenuPadding
            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(state.photos) { item ->
                Text(
                    "Item ${item.uri}",
                    Modifier
                        .border(1.dp, Color.Blue)
                        .width(150.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrashBinTabContentSystemTrashPreview() {
    PhotoCleanerTheme {
        TrashBinTabContent(
            state = TrashBinScreenState(
                currentTab = TabType.TrashBin,
                photos = persistentListOf()
            ),
            sendEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TrashBinTabContentInstantPreview() {
    PhotoCleanerTheme {
        TrashBinTabContent(
            state = TrashBinScreenState(
                currentTab = TabType.ToDeletion,
                photos = persistentListOf()
            ),
            sendEvent = {}
        )
    }
}