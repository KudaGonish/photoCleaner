package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.core_ui.elements.containers.SwipeableCard

data class Item(
    val name: String,
    val background: Color,
    val size: DpSize
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ClearingTabContent() {
    val items = remember {
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
    }

    val stackItemProvider = remember(items) {
        object : LazyLayoutItemProvider {
            override val itemCount: Int
                get() = items.size

            @Composable
            override fun Item(index: Int, key: Any) {
                val item = items.getOrNull(index) ?: return
                SwipeableCard(
                    onSwiped = { direction ->
                        items.remove(item)
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

            override fun getKey(index: Int): Any = items.getOrNull(index)?.name ?: "empty"
            override fun getIndex(key: Any): Int = items.indexOfFirst { it.name == key }
        }
    }

    val measurePolicy = { spend: LazyLayoutMeasureScope, constraints: Constraints ->
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
            maxWidth = constraints.maxWidth - with(spend) { 32.dp.roundToPx() }
        )

        val placeables = (0 until minOf(2, stackItemProvider.itemCount))
            .reversed()
            .map { placeableIndex ->
                spend.compose(placeableIndex).first().measure(looseConstraints)
            }

        val centerWidth = constraints.maxWidth / 2
        val centerHeight = constraints.maxHeight / 2

        spend.layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeble ->
                val x = centerWidth - placeble.width / 2
                val y = centerHeight - placeble.height / 2
                placeble.place(x, y)
            }
        }
    }
    LazyColumn() {
        item {

        }
    }
    LazyLayout(
        itemProvider = { stackItemProvider },
        measurePolicy = measurePolicy,
        modifier = Modifier.fillMaxSize()
    )
}

interface CustomLazyListScope {
    fun items(items: ImmutableList<Item>, content: @Composable () -> Unit)
}

class CustomLazyListScopeImpl : CustomLazyListScope {
    override fun items(
        items: ImmutableList<Item>,
        content: @Composable (() -> Unit)
    ) {
        TODO("Not yet implemented")
    }

}


class StackItemProvider(private val items: ImmutableList<Item>) : LazyLayoutItemProvider {
    override val itemCount: Int
        get() = items.size

    @Composable
    override fun Item(index: Int, key: Any) {
        val item = items.getOrNull(index) ?: return
        SwipeableCard(
            onSwiped = { direction ->
                items.remove(item)
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

    override fun getKey(index: Int): Any = items.getOrNull(index)?.name ?: "empty"
    override fun getIndex(key: Any): Int = items.indexOfFirst { it.name == key }
}
