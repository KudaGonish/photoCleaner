package ru.kudagonish.photocleaner.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.kudagonish.core_ui.elements.containers.blurBlobs.BlurBlobsPositions

internal class SplashAnimationState(
    margin: Float,
    screenWidthPx: Float,
    screenHeightPx: Float,
    private val targetPositions: BlurBlobsPositions
) {
    val blurRadius = Animatable(0f)

    val topLeftOffset = Animatable(
        initialValue = Offset(-margin, -margin),
        typeConverter = Offset.VectorConverter
    )
    val bottomRightOffset = Animatable(
        initialValue = Offset(screenWidthPx + margin, screenHeightPx + margin),
        typeConverter = Offset.VectorConverter
    )
    val bottomLeftOffset = Animatable(
        initialValue = Offset(-margin, screenHeightPx + margin),
        typeConverter = Offset.VectorConverter
    )
    val topRightOffset = Animatable(
        initialValue = Offset(screenWidthPx + margin, -margin),
        typeConverter = Offset.VectorConverter
    )

    suspend fun startAnimation(
        onStarted: () -> Unit,
        onFinished: () -> Unit
    ) {
        onStarted()

        coroutineScope {
            with(targetPositions) {
                launch {
                    topLeftOffset.animateTo(
                        targetValue = circle1,
                        animationSpec = getBlobTweenAnimation()
                    )
                }
                launch {
                    bottomRightOffset.animateTo(
                        targetValue = circle2,
                        animationSpec = getBlobTweenAnimation()
                    )
                }
                launch {
                    bottomLeftOffset.animateTo(
                        targetValue = circle3,
                        animationSpec = getBlobTweenAnimation()
                    )
                }
                launch {
                    topRightOffset.animateTo(
                        targetValue = circle4,
                        animationSpec = getBlobTweenAnimation()
                    )
                }
            }
        }

        blurRadius.animateTo(
            targetValue = blurSize,
            animationSpec = tween(blurTweenDuration)
        )
        onFinished()
    }

    private fun <T> getBlobTweenAnimation() = tween<T>(blobsTweenDuration)
    private val blobsTweenDuration = 1000
    private val blurTweenDuration = 600
    private val blurSize = 80f
}

@Composable
internal fun rememberSplashAnimationState(
    targetPositions: BlurBlobsPositions,
    margin: Float,
    screenWidthPx: Float,
    screenHeightPx: Float
): SplashAnimationState {
    return remember(targetPositions, margin) {
        SplashAnimationState(
            margin = margin,
            targetPositions = targetPositions,
            screenWidthPx = screenWidthPx,
            screenHeightPx = screenHeightPx
        )
    }
}