package ru.kudagonish.core_ui.elements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmer(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_progress"
    )

    drawBehind {
        val width = size.width
        val height = size.height
        
        // Мапим прогресс (0..1) на смещение (-2*width .. 2*width)
        val startOffsetX = width * (progress * 4 - 2)

        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFEBEBEB),
                    Color(0xFFF4F4F4),
                    Color(0xFFEBEBEB),
                ),
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + width, height)
            )
        )
    }
}
