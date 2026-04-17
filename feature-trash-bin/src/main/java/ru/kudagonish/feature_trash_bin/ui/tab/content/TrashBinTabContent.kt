package ru.kudagonish.feature_trash_bin.ui.tab.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ru.kudagonish.core_ui.elements.bottomMenuPadding
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_trash_bin.ui.tab.ui.ImageItem
import ru.kudagonish.feature_trash_bin.ui.tab.ui.Topbar
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
        Topbar(
            modifier = Modifier.weight(1f),
            tabState = state.currentTab,
            onTabClick = { sendEvent(Event.OnTabClick(it)) },
            onDeleteAllClick = { sendEvent(Event.OnDeleteAllClick) }
        )
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 8.dp,
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = bottomMenuPadding,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(state.photos, key = { it.src }) { item ->
                ImageItem(
                    src = item.src,
                    onClick = {}
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