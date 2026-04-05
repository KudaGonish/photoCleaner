package ru.kudagonish.core_ui.elements.containers.pager.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kudagonish.core_ui.elements.UiText

@Stable
data class PagerItem(
    val icon: ImageVector,
    val title: UiText,
    val content: @Composable () -> Unit
)