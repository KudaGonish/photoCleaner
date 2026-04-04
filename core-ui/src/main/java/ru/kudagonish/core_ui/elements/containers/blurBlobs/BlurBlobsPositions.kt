package ru.kudagonish.core_ui.elements.containers.blurBlobs

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset

@Stable
data class BlurBlobsPositions(
    val circle1: Offset,
    val circle2: Offset,
    val circle3: Offset,
    val circle4: Offset
) {
    companion object {
        fun default(width: Float, height: Float, density: Float): BlurBlobsPositions {
            return BlurBlobsPositions(
                circle1 = Offset(0f, 0f),
                circle2 = Offset(width, height),
                circle3 = Offset(0f, height * 0.7f),
                circle4 = Offset(width, 100f * density)
            )
        }
    }
}