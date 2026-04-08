package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import ru.kudagonish.core_ui.elements.containers.SwipeableCard
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.LazyStackBox
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.LazyStackScope
import ru.kudagonish.feature_clearing.ui.tab.content.lazyStack.scope.items

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
    /*
        val photoItems = remember {
            mutableStateListOf(
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
        }*/

    val stackItemProvider = remember(photoItems) {
        object : LazyLayoutItemProvider {
            override val itemCount: Int
                get() = photoItems.size

            @Composable
            override fun Item(index: Int, key: Any) {
                val item = photoItems.getOrNull(index) ?: return
                SwipeableCard(
                    onSwiped = { direction ->
                        photoItems = photoItems.remove(item)
                    }
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

            override fun getKey(index: Int): Any = photoItems.getOrNull(index)?.name ?: "empty"
            override fun getIndex(key: Any): Int = photoItems.indexOfFirst { it.name == key }
        }
    }


    LazyColumn() {
        items(photoItems) {

        }
    }

    LazyStackBox(modifier = Modifier) {
        items(photoItems) { item: Item ->
            SwipeableCard(
                onSwiped = { direction ->
                    photoItems = photoItems.remove(item)
                }
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