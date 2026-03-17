package ru.kudagonish.core_ui.elements.containers.blurBlobs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .blur(
                radius = blurRadius,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
    ) {
        val finalPositions = positions ?: BlurBlobsPositions.default(
            width = size.width,
            height = size.height,
            density = 1.dp.toPx()
        )

        drawCircle(
            color = Color(0xFF90CAF9).copy(alpha = 0.7f),
            radius = 400.dp.toPx(),
            center = finalPositions.circle1
        )

        drawCircle(
            color = Color(0xFFF48FB1).copy(alpha = 0.7f),
            radius = 550.dp.toPx(),
            center = finalPositions.circle2
        )

        drawCircle(
            color = Color(0xFFB39DDB).copy(alpha = 0.6f),
            radius = 350.dp.toPx(),
            center = finalPositions.circle3
        )

        drawCircle(
            color = Color(0xFFFFF59D).copy(alpha = 0.6f),
            radius = 250.dp.toPx(),
            center = finalPositions.circle4
        )
    }
}