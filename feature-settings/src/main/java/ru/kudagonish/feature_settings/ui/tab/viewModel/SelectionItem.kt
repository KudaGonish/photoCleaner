package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

internal data class SelectionItem(
    val isSelected: Boolean,
    @StringRes val title: Int? = null,
    @StringRes val description: Int? = null,
    val icon: ImageVector? = null,
    val color: Color? = null
)
