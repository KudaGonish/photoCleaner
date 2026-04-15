package ru.kudagonish.feature_clearing.ui.tab.lazyStack

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import ru.kudagonish.core_ui.elements.containers.SwipeDirection
import ru.kudagonish.core_ui.theme.LocalCustomColors
import ru.kudagonish.feature_clearing.ui.tab.lazyStack.scope.LazyStackScope


@Composable
internal fun LazyStackBox(
    modifier: Modifier = Modifier,
    stackState: LazyStackState,
    content: LazyStackScope.() -> Unit
) {
    val itemProvider = rememberStackItemProviderLambda(content, stackState)
    val measurePolicy = rememberStackMeasurePolicy(stackState, itemProvider)
    val deletionColor = LocalCustomColors.current.instant
    val saveColor = LocalCustomColors.current.safe
    LazyLayout(
        modifier = Modifier
            .fillMaxSize()
            .drawWithCache {
                val width = size.width
                val height = size.height
                val barWidth = width * 0.20f

                val redBrush = Brush.horizontalGradient(
                    colors = listOf(deletionColor.copy(alpha = 0.6f), Color.Transparent),
                    startX = 0f,
                    endX = barWidth
                )
                val greenBrush = Brush.horizontalGradient(
                    colors = listOf(Color.Transparent, saveColor.copy(alpha = 0.6f)),
                    startX = width - barWidth,
                    endX = width
                )
                onDrawWithContent {
                    drawContent()
                    val swipeDirection = stackState.swipeDirection ?: return@onDrawWithContent

                    if (swipeDirection == SwipeDirection.Left) {
                        drawRect(
                            brush = redBrush,
                            topLeft = Offset.Zero,
                            size = Size(barWidth, height),
                            alpha = 0.3f
                        )
                    } else {
                        drawRect(
                            brush = greenBrush,
                            topLeft = Offset(width - barWidth, 0f),
                            size = Size(barWidth, height),
                            alpha = 0.4f
                        )
                    }
                }
            } then modifier,
        itemProvider = itemProvider,
        measurePolicy = measurePolicy,
    )
}