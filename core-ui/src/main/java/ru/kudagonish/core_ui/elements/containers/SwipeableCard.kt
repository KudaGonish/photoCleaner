package ru.kudagonish.core_ui.elements.containers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.util.fastIsFinite
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class SwipeDirection {
    Left, Right, None
}

@Composable
fun SwipeableCard(
    onSwiped: (SwipeDirection) -> Unit,
    onOffsetChange: (Int) -> Unit ,
    content: @Composable () -> Unit
) {
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(offset) {
        snapshotFlow { offset.value.x }
            .filter { it.fastIsFinite() }
            .collect { onOffsetChange(it.roundToInt()) }
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                rotationZ = offset.value.x / 30f
                this.translationX = offset.value.x
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            if (offset.value.x > 400) {
                                offset.animateTo(
                                    targetValue = Offset(1000f, offset.value.y),
                                    animationSpec = tween(300)
                                )
                                onSwiped(SwipeDirection.Right)
                            } else if (offset.value.x < -400) {
                                offset.animateTo(
                                    targetValue = Offset(-1000f, offset.value.y),
                                    animationSpec = tween(300)
                                )
                                onSwiped(SwipeDirection.Left)
                            } else {
                                offset.animateTo(
                                    targetValue = Offset.Zero,
                                    animationSpec = tween(300)
                                )
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offset.snapTo(offset.value + dragAmount)
                        }
                    }
                )
            }
    ) {
        content()
    }
}