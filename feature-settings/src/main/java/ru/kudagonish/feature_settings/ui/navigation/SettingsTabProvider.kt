package ru.kudagonish.feature_settings.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewCozy
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_settings.R
import ru.kudagonish.feature_settings.ui.tab.SettingsTab

fun settingsTabItem(): PagerItem {
    return PagerItem(
        icon = Icons.Outlined.Settings,
        title = R.string.tab_settings,
        content = { SettingsTab() }
    )
}