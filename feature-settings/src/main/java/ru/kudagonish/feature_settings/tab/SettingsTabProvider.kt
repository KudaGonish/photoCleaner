package ru.kudagonish.feature_settings.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ViewCozy
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_settings.R

fun settingsTabItem(): PagerItem {
    return PagerItem(
        icon = Icons.Outlined.ViewCozy,
        title = R.string.tab_settings,
        content = { SettingsTab() }
    )
}