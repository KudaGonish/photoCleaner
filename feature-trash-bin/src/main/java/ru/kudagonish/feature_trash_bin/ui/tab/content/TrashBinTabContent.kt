package ru.kudagonish.feature_trash_bin.ui.tab.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.collections.immutable.persistentListOf
import ru.kudagonish.core_ui.elements.bottomMenuPadding
import ru.kudagonish.core_ui.elements.shadow
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
        Row(Modifier.padding(start = 16.dp, end = 0.dp)) {
            SingleChoiceSegmentedButtons(
                modifier = Modifier.weight(1f),
                tabState = state.currentTab,
                onTabClick = { sendEvent(Event.OnTabClick(it)) },
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {}
                    .padding(8.dp),
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = null
            )
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 8.dp,
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = bottomMenuPadding,
                start = 16.dp,
                end = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = StaggeredGridCells.Fixed(2)
        ) {
            items(state.photos, key = { it.src }) { item ->
                var isLoaded by remember { mutableStateOf(false) }
                var contentScale by remember { mutableStateOf(ContentScale.Fit) }

                AsyncImage(
                    model = item.src,
                    contentDescription = null,
                    onSuccess = { painterState ->
                        isLoaded = true
                        val size = painterState.painter.intrinsicSize
                        if (size.width > 0 && size.height > 0) {
                            val ratio = size.width / size.height
                            contentScale =
                                if (ratio > 1.0f) ContentScale.Fit else ContentScale.FillWidth
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (!isLoaded) Modifier
                            else Modifier.shadow()
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.background),
                    contentScale = contentScale,
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