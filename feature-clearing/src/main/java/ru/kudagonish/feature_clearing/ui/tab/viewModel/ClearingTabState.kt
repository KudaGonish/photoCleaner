package ru.kudagonish.feature_clearing.ui.tab.viewModel

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class ClearingTabState(
    val countToDelete: Int = 0,
    val countToTrash: Int = 0,
    val images: ImmutableList<ImageUiModel> = persistentListOf()
)