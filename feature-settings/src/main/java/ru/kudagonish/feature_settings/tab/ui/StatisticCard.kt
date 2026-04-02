package ru.kudagonish.feature_settings.tab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun StatisticCard() {
    Box(
        Modifier
            .clip(itemsRoundedShape)
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Blue.copy(alpha = 0.7f))
    )
}