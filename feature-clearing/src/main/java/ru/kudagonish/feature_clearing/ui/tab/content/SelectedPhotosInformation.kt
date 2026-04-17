package ru.kudagonish.feature_clearing.ui.tab.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.shadow
import ru.kudagonish.core_ui.theme.LocalCustomColors
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_clearing.R

@Composable
internal fun SelectedPhotosInformation(
    countToTrash: Int,
    countToDelete: Int,
    onMoveToTrashClick: () -> Unit,
    onViewDeletionItemsClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (countToTrash > 0) {
            InformationSnackbar(
                title = stringResource(R.string.to_trash_count),
                count = countToTrash.toString(),
            ) {
                ActionContainer(
                    modifier = Modifier.clickable(onClick = onMoveToTrashClick),
                    shape = CircleShape,
                    paddings = PaddingValues(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        tint = LocalCustomColors.current.bin,
                        contentDescription = null
                    )
                }
            }
        }

        if (countToDelete > 0) {
            InformationSnackbar(
                title = stringResource(R.string.to_deletion_count),
                count = countToDelete.toString(),
            ) {
                ActionContainer(
                    modifier = Modifier.clickable(onClick = onViewDeletionItemsClick),
                    shape = CircleShape,
                    paddings = PaddingValues(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.RemoveRedEye,
                        tint = LocalCustomColors.current.bin,
                        contentDescription = null
                    )
                }
                ActionContainer(
                    modifier = Modifier.clickable(onClick = onDeleteClick),
                    shape = CircleShape,
                    paddings = PaddingValues(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Bolt,
                        tint = LocalCustomColors.current.instant,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
private fun InformationSnackbar(
    title: String,
    count: String,
    iconsContent: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionContainer {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = W600)) {
                        append("$title ")
                    }
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                        append(count)
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.weight(1f))
        iconsContent()
    }
}

@Composable
private fun ActionContainer(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    paddings: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .shadow(shape = shape, alpha = 0.2f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surface)
            .then(modifier)
            .padding(paddings),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Preview(locale = "ru")
@Composable
private fun Preview() {
    PhotoCleanerTheme {
        Box(Modifier.background(MaterialTheme.colorScheme.background)) {
            SelectedPhotosInformation(
                countToTrash = 1,
                countToDelete = 1,
                onMoveToTrashClick = {},
                onDeleteClick = {},
                onViewDeletionItemsClick = { }
            )
        }
    }
}