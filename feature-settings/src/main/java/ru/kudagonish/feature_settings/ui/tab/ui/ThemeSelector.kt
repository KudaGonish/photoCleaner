package ru.kudagonish.feature_settings.ui.tab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.feature_settings.ui.tab.viewModel.SelectionItem

@Composable
internal fun ThemeSelector(
    themes: ImmutableList<SelectionItem<AppTheme>>,
    onSelectItem: (AppTheme) -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(vertical = 4.dp, horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        themes.forEach { theme ->
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        if (!theme.isSelected) Modifier
                        else Modifier.background(MaterialTheme.colorScheme.secondary)
                    )
                    .clickable(
                        enabled = !theme.isSelected,
                        onClick = { onSelectItem(theme.setting) }
                    )
                    .padding(horizontal = 4.dp),
                imageVector = theme.icon!!,
                contentDescription = null,
                tint = if (theme.isSelected) MaterialTheme.colorScheme.onSecondary
                else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}