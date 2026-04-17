package ru.kudagonish.feature_trash_bin.ui.tab.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.LocalCustomColors
import ru.kudagonish.feature_trash_bin.R

@Composable
internal fun ActionPopUp(
    modifier: Modifier,
    isVisible: Boolean,
    onRestore: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    DropdownMenu(
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
        expanded = isVisible,
        onDismissRequest = onDismiss,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onRestore()

                    onDismiss()
                }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            text = stringResource(R.string.dropdown_restore_one),
            style = MaterialTheme.typography.titleMedium,
            color = LocalCustomColors.current.bin,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDelete()

                    onDismiss()
                }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            text = stringResource(R.string.dropdown_delete_one),
            style = MaterialTheme.typography.titleMedium,
            color = LocalCustomColors.current.instant,
            textAlign = TextAlign.Center
        )
    }
}