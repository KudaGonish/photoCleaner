package ru.kudagonish.core_ui.elements.containers.pager.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class PagerItem(
    val icon: ImageVector,
    val title: String,
    val content: @Composable () -> Unit
)