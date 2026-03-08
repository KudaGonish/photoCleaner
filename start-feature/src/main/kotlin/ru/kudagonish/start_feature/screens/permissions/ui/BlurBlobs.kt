package ru.kudagonish.start_feature.screens.permissions.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.start_feature.util.composableCallback

@Composable
internal fun BlurBlobsContainer(
    background: Color = Color.White,
    content: composableCallback
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        BlurBlobsCanvas()
        content()
    }
}


@Composable
private fun BlurBlobsCanvas() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .blur(
                radius = 80.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
    ) {
        drawCircle(
            color = Color(0xFF90CAF9).copy(alpha = 0.7f),
            radius = 400.dp.toPx(),
            center = Offset(0f, 0f)
        )

        drawCircle(
            color = Color(0xFFF48FB1).copy(alpha = 0.7f),
            radius = 550.dp.toPx(),
            center = Offset(size.width, size.height)
        )

        drawCircle(
            color = Color(0xFFB39DDB).copy(alpha = 0.6f),
            radius = 350.dp.toPx(),
            center = Offset(0f, size.height * 0.7f)
        )

        drawCircle(
            color = Color(0xFFFFF59D).copy(alpha = 0.6f),
            radius = 250.dp.toPx(),
            center = Offset(size.width, 100.dp.toPx())
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BlurBlobsContainerPreview() {
    PhotoCleanerTheme {
        BlurBlobsContainer {
            // Preview content
        }
    }
}