package ru.kudagonish.photocleaner.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.containers.blurBlobs.BlurBlobsCanvas
import ru.kudagonish.core_ui.elements.containers.blurBlobs.BlurBlobsPositions





@Composable
fun SplashBlurBlobs(
    onStarted: () -> Unit,
    onAnimationFinished: () -> Unit
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current

    val margin = with(density) { 400.dp.toPx() }
    val screenWidthPx = containerSize.width.toFloat()
    val screenHeightPx = containerSize.height.toFloat()
    val densityValue = with(density) { 1.dp.toPx() }

    val targetPositions = remember(screenWidthPx, screenHeightPx, densityValue) {
        BlurBlobsPositions.default(screenWidthPx, screenHeightPx, densityValue)
    }

    val state = rememberSplashAnimationState(targetPositions, margin, screenWidthPx, screenHeightPx)

    LaunchedEffect(Unit) {
        state.startAnimation(
            onStarted = onStarted,
            onFinished = onAnimationFinished
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        BlurBlobsCanvas(
            blurRadius = state.blurRadius.value.dp,
            positions = BlurBlobsPositions(
                circle1 = state.topLeftOffset.value,
                circle2 = state.bottomRightOffset.value,
                circle3 = state.bottomLeftOffset.value,
                circle4 = state.topRightOffset.value
            )
        )
    }
}
