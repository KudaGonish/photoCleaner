package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import androidx.compose.runtime.Stable

@Stable
internal data class TrashBinScreenState(
    val currentTab: TabType = TabType.TrashBin
)

@Stable
internal sealed interface TabType {
    data object TrashBin : TabType
    data object ToDeletion : TabType
}