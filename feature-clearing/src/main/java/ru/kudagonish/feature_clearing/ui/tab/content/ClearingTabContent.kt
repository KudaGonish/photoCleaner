package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.bottomMenuPadding
import ru.kudagonish.core_ui.elements.containers.SwipeDirection
import ru.kudagonish.core_ui.elements.containers.SwipeableCard
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.LazyStackBox
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.rememberLazyStackState
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.items
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabState
import ru.kudagonish.feature_clearing.ui.tab.viewModel.ClearingTabViewModel.Event

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClearingTabContent(
    state: ClearingTabState,
    sendEvent: (Event) -> Unit
) {
    val stackState = rememberLazyStackState()
    Column(
        Modifier
            .padding(bottom = bottomMenuPadding, top = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(state.countToTrash != 0 || state.countToDelete != 0) {
            SelectedPhotosInformation(
                countToTrash = state.countToTrash,
                countToDelete = state.countToDelete,
                onMoveToTrashClick = { },
                onViewDeletionItemsClick = { },
                onDeleteClick = { }
            )
        }
        LazyStackBox(stackState = stackState) {
            items(state.images, key = { it.src }) { index, item ->
                SwipeableCard(
                    onSwiped = { direction ->
                        val event = when (direction) {
                            SwipeDirection.Right -> Event.KeepPhoto(item)
                            else -> Event.DeletePhoto(item)
                        }
                        sendEvent(event)
                    },
                    onSwipeDirectionChanged = { stackState.updateSwipeDirection(it) },
                    enabled = index == 0,
                    onOffsetChange = { stackState.updateTopItemOffset(it) },
                    imageSrc = item.src,
                )
            }
        }
    }
}