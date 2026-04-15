package ru.kudagonish.core_ui.elements.containers.blurBlobs

import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.LocalCustomColors

@Composable
fun BlurBlobsContainer(
    modifier: Modifier = Modifier,
    background: Color = Color.White,
    blurRadius: Dp = 80.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {
        BlurBlobsCanvas(blurRadius = blurRadius)
        content()
    }
}

@Composable
fun BlurBlobsCanvas(
    modifier: Modifier = Modifier,
    blurRadius: Dp = 80.dp,
    positions: BlurBlobsPositions? = null
) {
    val colorOfBubbles = LocalCustomColors.current.bubbleGradients
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .blur(
                radius = blurRadius,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
    ) {
        val bubble1Radius = 400.dp.toPx()
        val bubble2Radius = 550.dp.toPx()
        val bubble3Radius = 350.dp.toPx()
        val bubble4Radius = 250.dp.toPx()
        val finalPositions = positions ?: BlurBlobsPositions.default(
            width = size.width,
            height = size.height,
            density = 1.dp.toPx()
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            drawCircle(
                brush = Brush.radialGradient(
                    0.0f to colorOfBubbles[0],
                    1.0f to Color.Transparent,
                    center = finalPositions.circle1,
                    radius = bubble1Radius
                ),
                radius = bubble1Radius,
                center = finalPositions.circle1
            )
            drawCircle(
                brush = Brush.radialGradient(
                    0.0f to colorOfBubbles[1],
                    1.0f to Color.Transparent,
                    center = finalPositions.circle2,
                    radius = bubble2Radius
                ),
                radius = bubble2Radius,
                center = finalPositions.circle2
            )
            drawCircle(
                brush = Brush.radialGradient(
                    0.0f to colorOfBubbles[2],
                    1.0f to Color.Transparent,
                    center = finalPositions.circle3,
                    radius = bubble3Radius
                ),
                radius = bubble3Radius,
                center = finalPositions.circle3
            )
            drawCircle(
                brush = Brush.radialGradient(
                    0.0f to colorOfBubbles[3],
                    1.0f to Color.Transparent,
                    center = finalPositions.circle4,
                    radius = bubble4Radius
                ),
                radius = bubble4Radius,
                center = finalPositions.circle4
            )
        } else {
            drawCircle(
                color = colorOfBubbles[0].copy(alpha = 0.7f),
                radius = bubble1Radius,
                center = finalPositions.circle1
            )
            drawCircle(
                color = colorOfBubbles[1].copy(alpha = 0.7f),
                radius = bubble2Radius,
                center = finalPositions.circle2
            )
            drawCircle(
                color = colorOfBubbles[2].copy(alpha = 0.6f),
                radius = bubble3Radius,
                center = finalPositions.circle3
            )
            drawCircle(
                color = colorOfBubbles[3].copy(alpha = 0.6f),
                radius = bubble4Radius,
                center = finalPositions.circle4
            )
        }
    }
}