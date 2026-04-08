package ru.kudagonish.feature_clearing.ui.tab.content

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ru.kudagonish.core_ui.elements.containers.SwipeableCard
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.LazyStackBox
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.items
import kotlin.math.abs

data class Item(
    val name: String,
    val background: Color,
    val size: DpSize
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClearingTabContent() {
    var photoItems by remember {
        mutableStateOf(
            persistentListOf(
                Item(
                    name = "Фото 1",
                    background = Color.Red,
                    size = DpSize(400.dp, 300.dp)
                ),
                Item(
                    name = "Фото 2",
                    background = Color.Blue,
                    size = DpSize(300.dp, 450.dp)
                ),
                Item(
                    name = "Фото 3",
                    background = Color.Green,
                    size = DpSize(400.dp, 300.dp)
                ),
                Item(
                    name = "Фото 4",
                    background = Color.Yellow,
                    size = DpSize(300.dp, 450.dp)
                ),
                Item(
                    name = "Фото 5",
                    background = Color.Magenta,
                    size = DpSize(300.dp, 450.dp)
                )
            )
        )
    }
    val stackState = rememberLazyStackState()
    val z = rememberLazyListState()
    LazyStackBox(modifier = Modifier, stackState = stackState) {
        items(photoItems) { item: Item ->
            SwipeableCard(
                onSwiped = { photoItems = photoItems.remove(item) },
                onOffsetChange = { stackState.updateTopItemOffset(it) }
            ) {
                Box(
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
                }
            }
        }
    }
}

@Stable
internal class LazyStackState() {
    private var _offset = mutableIntStateOf(0)

    val itemScaling: Float
        get() = (abs(_offset.intValue) / 900f).coerceIn(0f, 1f)

    val itemAlpha: Float
        get()= (abs(_offset.intValue) / 600f).coerceIn(0f,1f)

    fun updateTopItemOffset(value: Int) {
        _offset.intValue = value
    }
}

@Composable
internal fun rememberLazyStackState() = remember { LazyStackState() }