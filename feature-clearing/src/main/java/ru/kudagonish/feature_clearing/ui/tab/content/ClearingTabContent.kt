package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
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
    BoxWithConstraints {
        val maxCardHeight = (this.maxHeight.value * 0.75f).dp
        LazyStackBox(
            modifier = Modifier.padding(bottom = bottomMenuPadding, top = 36.dp),
            stackState = stackState
        ) {
            items(state.images, key = { it.src }) { index, item ->
                SwipeableCard(
                    onSwiped = { direction ->
                        val event = when (direction) {
                            SwipeDirection.Right -> Event.SaveImage(item)
                            else -> Event.DeleteImage(item)
                        }
                        sendEvent(event)
                    },
                    onSwipeDirectionChanged = { stackState.updateSwipeDirection(it) },
                    enabled = index == 0,
                    onOffsetChange = { stackState.updateTopItemOffset(it) },
                    imageSrc = item.src,
                    cardMaxHeight = maxCardHeight
                )
            }
        }
    }
}