package ru.kudagonish.feature_trash_bin.ui.tab.viewModel

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
internal data class TrashBinScreenState(
    val currentTab: TabType = TabType.TrashBin,
    val photos: ImmutableList<PhotoInformation> = persistentListOf(
        PhotoInformation("1"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("2"),
        PhotoInformation("3")
    )
)

@Stable
internal sealed interface TabType {
    data object TrashBin : TabType
    data object ToDeletion : TabType
}

@Stable
internal data class PhotoInformation(
    val uri: String
)