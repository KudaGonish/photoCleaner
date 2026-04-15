package ru.kudagonish.feature_trash_bin.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteSweep
import ru.kudagonish.core_ui.elements.UiText
import ru.kudagonish.core_ui.elements.containers.pager.models.PagerItem
import ru.kudagonish.feature_trash_bin.R
import ru.kudagonish.feature_trash_bin.ui.tab.TrashBinTab

fun trashBinTabItem(): PagerItem {
    return PagerItem(
        icon = Icons.Outlined.DeleteSweep,
        title = UiText.StringResource(R.string.tab_trash_bin),
        content = { TrashBinTab() }
    )
}