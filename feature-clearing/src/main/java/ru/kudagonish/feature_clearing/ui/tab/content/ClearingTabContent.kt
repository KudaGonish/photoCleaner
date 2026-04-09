package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.kudagonish.core_ui.elements.bottomMenuPadding
import ru.kudagonish.core_ui.elements.containers.SwipeDirection
import ru.kudagonish.core_ui.elements.containers.SwipeableCard
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabState
import ru.kudagonish.feature_clearing.ui.tab.ClearingTabViewModel.Event
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.LazyStackBox
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.items
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClearingTabContent(
    state: ClearingTabState,
    sendEvent: (Event) -> Unit
) {
    val stackState = rememberLazyStackState()

    LazyStackBox(
        modifier = Modifier
            .padding(bottom = bottomMenuPadding + 32.dp, top = 36.dp),
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
                enabled = index == 0,
                onOffsetChange = { stackState.updateTopItemOffset(it) }
            ) {
                AsyncImage(
                    modifier = Modifier
                        .dropShadow(
                            shape = RoundedCornerShape(16.dp),
                            shadow = Shadow(
                                radius = 6.dp,
                                color = Color.Black,
                                alpha = 0.2f
                            )
                        )
                        .clip(RoundedCornerShape(16.dp)),
                    model = item.src,
                    contentDescription = null
                )
                /*                Box(
                                    modifier = Modifier
                                        .size(item.size)
                                        .dropShadow(
                                            shape = RoundedCornerShape(16.dp),
                                            shadow = Shadow(
                                                radius = 6.dp,
                                                color = Color.Black,
                                                alpha = 0.2f
                                            )
                                        )
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(item.background),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = Color.White
                                    )
                                }*/
            }
        }
    }
}

@Stable
internal class LazyStackState {
    private var _offset = mutableIntStateOf(0)

    val itemScaling: Float
        get() = (abs(_offset.intValue) / 900f).coerceIn(0f, 1f)

    val itemAlpha: Float
        get() = (abs(_offset.intValue) / 600f).coerceIn(0f, 1f)

    fun updateTopItemOffset(value: Int) {
        _offset.intValue = value
    }
}

@Composable
internal fun rememberLazyStackState() = remember { LazyStackState() }
