package ru.kudagonish.feature_trash_bin.ui.tab.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SingleChoiceSegmentedButtonRowScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import ru.kudagonish.feature_trash_bin.R
import ru.kudagonish.feature_trash_bin.ui.tab.viewModel.TabType

@Composable
internal fun Topbar(
    modifier: Modifier = Modifier,
    tabState: TabType,
    onTabClick: (TabType) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    var menuIsVisible by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleChoiceSegmentedButtonRow(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
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
        Box {
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { menuIsVisible = true }
                    .padding(8.dp),
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = null
            )
            DropdownMenu(
                shape = RoundedCornerShape(16.dp),
                offset = DpOffset(x = (-8).dp, y = 0.dp),
                properties = PopupProperties(clippingEnabled = true),
                containerColor = MaterialTheme.colorScheme.surface,
                expanded = menuIsVisible,
                onDismissRequest = { menuIsVisible = false }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            menuIsVisible = false
                            onDeleteAllClick()
                        }
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    text = stringResource(R.string.dropdown_delete_all),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
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
