package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasurePolicy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@Composable
internal fun rememberStackMeasurePolicy(
    stackState: LazyStackState,
    itemProvider: () -> LazyLayoutItemProvider
) = remember {
    LazyLayoutMeasurePolicy { constraints ->
        val provider = itemProvider()

        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
            maxWidth = constraints.maxWidth - with(this) { 32.dp.roundToPx() }
        )

        val placeableList = (0 until minOf(2, provider.itemCount))
            .reversed()
            .map { placeableIndex ->
                compose(placeableIndex).first().measure(looseConstraints)
            }

        val centerWidth = constraints.maxWidth / 2
        val centerHeight = constraints.maxHeight / 2

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeableList.fastForEachIndexed { index, placeable ->
                val x = centerWidth - placeable.width / 2
                val y = centerHeight - placeable.height / 2

                if (index == 0 && placeableList.size > 1) {
                    placeable.placeWithLayer(x, y) {
                        scaleX = stackState.itemScaling
                        scaleY = stackState.itemScaling
                        alpha = stackState.itemAlpha
                    }
                } else {
                    placeable.place(x, y)
                }
            }
        }
    }
}