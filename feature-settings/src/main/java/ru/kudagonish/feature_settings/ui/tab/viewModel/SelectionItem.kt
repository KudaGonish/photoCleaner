package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
internal data class SelectionItem<SettingType>(
    val setting: SettingType,
    val isSelected: Boolean,
    @StringRes val title: Int? = null,
    @StringRes val description: Int? = null,
    val icon: ImageVector? = null
)
