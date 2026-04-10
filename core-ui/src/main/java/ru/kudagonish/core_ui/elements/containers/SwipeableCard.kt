package ru.kudagonish.core_ui.elements.containers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastIsFinite
import coil3.compose.AsyncImage
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
    modifier: Modifier,
    onSwiped: (SwipeDirection) -> Unit,
    onOffsetChange: (Int) -> Unit,
    enabled: Boolean = true,
    imageSrc: String,
    cardMaxHeight: Dp
) {
    val scope = rememberCoroutineScope()
    val offset = remember { Animatable(Offset.Zero, Offset.VectorConverter) }
    var contentScale by remember { mutableStateOf(ContentScale.Fit) }
    var isLoaded by remember { mutableStateOf(false) }
    var isFinishing by remember { mutableStateOf(false) }

    LaunchedEffect(offset, enabled, isFinishing) {
        if (enabled && !isFinishing) {
            snapshotFlow { offset.value.x }
                .filter { it.fastIsFinite() }
                .map { it.roundToInt() }
                .distinctUntilChanged()
                .collect { onOffsetChange(it) }
        }
    }

    Box(
        modifier = modifier
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
                                val targetValueX = if (currentX > 0) 1200f else -1200f
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
        AsyncImage(
            model = imageSrc,
            contentDescription = null,
            onSuccess = { painterState ->
                isLoaded = true
                val size = painterState.painter.intrinsicSize
                if (size.width > 0 && size.height > 0) {
                    val ratio = size.width / size.height
                    contentScale =
                        if (ratio > 1.0f) ContentScale.Fit else ContentScale.FillWidth
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!isLoaded) Modifier
                    else Modifier
                        .heightIn(max = cardMaxHeight)
                        .dropShadow(
                            shape = RoundedCornerShape(16.dp),
                            shadow = Shadow(
                                radius = 6.dp,
                                color = Color.Black,
                                alpha = 0.3f
                            )
                        )
                )
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background),
            contentScale = contentScale,
        )
    }
}