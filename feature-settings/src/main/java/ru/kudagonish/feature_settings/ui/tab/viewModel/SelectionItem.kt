package ru.kudagonish.feature_settings.ui.tab.viewModel

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kudagonish.core_ui.elements.UiText

@Stable
internal data class SelectionItem<SettingType>(
    val setting: SettingType,
    val isSelected: Boolean,
    val title: UiText? = null,
    val description: UiText? = null,
    val icon: ImageVector? = null
)
