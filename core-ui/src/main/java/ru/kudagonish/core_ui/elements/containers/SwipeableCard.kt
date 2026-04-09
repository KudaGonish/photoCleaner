package ru.kudagonish.core_ui.elements.containers

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastIsFinite
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

enum class SwipeDirection {
    Left, Right
}

@Composable
fun SwipeableCard(
    onSwiped: (SwipeDirection) -> Unit,
    onOffsetChange: (Int) -> Unit,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    val scope = rememberCoroutineScope()

    // Флаг, который предотвратит отправку обновлений после того, как мы решили свайпнуть карточку окончательно
    var isFinishing by remember { mutableStateOf(false) }

    LaunchedEffect(offset, enabled, isFinishing) {
        Log.d("TAG", "SwipeableCard: effect ${enabled} ${!isFinishing}")
        if (enabled && !isFinishing) {
            snapshotFlow { offset.value.x }
                .filter { it.fastIsFinite() }
                .map { it.roundToInt() }
                .distinctUntilChanged()
                .collect { onOffsetChange(it) }
        }
    }

    Box(
        modifier = Modifier
            .graphicsLayer {
                translationX = offset.value.x
                rotationZ = offset.value.x / 30f
            }
            .pointerInput(enabled) {
                if (!enabled) return@pointerInput

                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            val currentX = offset.value.x
                            if (abs(currentX) > 400) {
                                val targetValueX  = if (currentX > 0) 1200f else -1200f
                                offset.animateTo(
                                    targetValue = Offset(targetValueX, 0f),
                                    animationSpec = tween(300)
                                )
                                onOffsetChange(0)
                                isFinishing = true
                                onSwiped(if (currentX > 0) SwipeDirection.Right else SwipeDirection.Left)
                            } else {
                                offset.animateTo(Offset.Zero, tween(300))
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