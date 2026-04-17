package ru.kudagonish.core_ui.elements

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.shadow(
    shape: Shape = RoundedCornerShape(16.dp),
    radius: Dp = 6.dp,
    alpha: Float = 0.3f
) = this.dropShadow(
    shape = shape,
    shadow = Shadow(
        radius = radius,
        color = Color.Black,
        alpha = alpha
    )
)