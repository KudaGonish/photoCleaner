package ru.kudagonish.feature_clearing.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import ru.kudagonish.core_ui.elements.UiText
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_clearing.R
import ru.kudagonish.feature_clearing.ui.tab.ClearingTab

fun clearingTabItem(onNavigateToBinDeletionTab: () -> Unit): PagerItem {
    return PagerItem(
        icon = Icons.Outlined.Settings,
        title = UiText.StringResource(R.string.tab_clearing),
        content = { ClearingTab(onNavigateToBinDeletionTab = onNavigateToBinDeletionTab) }
    )
}