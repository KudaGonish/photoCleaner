package ru.kudagonish.feature_clearing.ui.tab.content.lazyStack

import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasurePolicy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
internal fun rememberStackMeasurePolicy(
    itemProvider: () -> LazyLayoutItemProvider
) = remember {
    LazyLayoutMeasurePolicy { constraints ->
        val provider = itemProvider()

        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
            maxWidth = constraints.maxWidth - with(this) { 32.dp.roundToPx() }
        )

        val placeables = (0 until minOf(2, provider.itemCount))
            .reversed()
            .map { placeableIndex ->
                compose(placeableIndex).first().measure(looseConstraints)
            }

        val centerWidth = constraints.maxWidth / 2
        val centerHeight = constraints.maxHeight / 2

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEach { placeble ->
                val x = centerWidth - placeble.width / 2
                val y = centerHeight - placeble.height / 2
                placeble.place(x, y)
            }
        }
    }
}