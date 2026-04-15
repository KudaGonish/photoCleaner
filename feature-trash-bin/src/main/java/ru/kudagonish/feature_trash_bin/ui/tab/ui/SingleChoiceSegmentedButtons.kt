package ru.kudagonish.feature_trash_bin.ui.tab.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kudagonish.feature_trash_bin.R
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TabType

@Composable
internal fun SingleChoiceSegmentedButtons(
    tabState: TabType,
    onTabClick: (TabType) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        SegmentedButtonConfigured(
            title = stringResource(R.string.selection_trash_bin),
            isSelected = tabState == TabType.TrashBin,
            onClick = { onTabClick(TabType.TrashBin) }
        )
        SegmentedButtonConfigured(
            title = stringResource(R.string.selection_to_deletion),
            isSelected = tabState == TabType.ToDeletion,
            onClick = { onTabClick(TabType.ToDeletion) }
        )
    }
}

@Composable
private fun SingleChoiceSegmentedButtonRowScope.SegmentedButtonConfigured(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) = SegmentedButton(
    modifier = Modifier
        .weight(1f)
        .height(40.dp),
    shape = RoundedCornerShape(16.dp),
    border = BorderStroke(0.dp, Color.Transparent),
    colors = SegmentedButtonDefaults.colors().copy(
        activeContainerColor = MaterialTheme.colorScheme.secondary,
        inactiveContainerColor = Color.Transparent
    ),
    onClick = onClick,
    selected = isSelected,
    icon = { },
    label = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onSecondary
            else MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
)