package ru.kudagonish.feature_settings.ui.tab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.feature_settings.ui.tab.viewModel.SelectionItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun DropdownMenu(
    languages: ImmutableList<SelectionItem<Language>>,
    onSelectItem: (Language) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedLanguage = languages
        .firstOrNull { it.isSelected }?.title
        ?.let { stringResource(it) } ?: ""

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(vertical = 4.dp)
                .padding(start = 8.dp, end = 4.dp)
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedLanguage,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(Modifier.width(8.dp))
            Icon(
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer { rotationZ = if (expanded) 90f else 270f },
                imageVector = Icons.Outlined.ChevronLeft,
                contentDescription = null,
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            onDismissRequest = { expanded = false },
        ) {
            languages.filter { !it.isSelected }.forEach { language ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(
                            onClick = {
                                expanded = false
                                onSelectItem(language.setting)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = stringResource(language.title!!),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}