package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TrashBinScreenState(
    val currentTab: TabType = TabType.TrashBin,
    val photos: ImmutableList<ImageUiModel> = persistentListOf()
)

@Stable
sealed interface TabType {
    data object TrashBin : TabType
    data object ToDeletion : TabType
}

@Stable
internal data class ImageUiModel(val src: String)