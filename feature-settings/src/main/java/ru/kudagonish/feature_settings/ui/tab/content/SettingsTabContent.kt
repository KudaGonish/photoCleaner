package ru.kudagonish.feature_settings.ui.tab.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.datastore.settings.models.AppTheme
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.datastore.settings.models.Language
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.R
import ru.kudagonish.feature_settings.ui.tab.ui.DoubleContentRow
import ru.kudagonish.feature_settings.ui.tab.ui.SettingsSection
import ru.kudagonish.feature_settings.ui.tab.ui.StatisticCard
import ru.kudagonish.feature_settings.ui.tab.ui.UtilizationRadioButton
import ru.kudagonish.feature_settings.ui.tab.ui.itemsRoundedShape
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabState
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Event
import ru.kudagonish.feature_settings.ui.tab.viewModel.mapAlgorithms
import ru.kudagonish.feature_settings.ui.tab.viewModel.mapDeletionTypes
import ru.kudagonish.feature_settings.ui.tab.viewModel.mapLanguages
import ru.kudagonish.feature_settings.ui.tab.viewModel.mapThemes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsTabContent(
    state: SettingsTabState,
    sendEvent: (Event) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        Box(
            modifier = Modifier.padding(top = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.tab_settings),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }
        StatisticCard()
        SettingsSection(
            titleIcon = Icons.Filled.ColorLens,
            titleText = R.string.selection_ui,
            content = {
                DoubleContentRow(
                    leftContent = {
                        Text(
                            text = "Язык"
                        )
                    },
                    rightContent = {
                        Text(
                            text = "\uD83C\uDDF7\uD83C\uDDFA Русский"
                        )
                    }
                )
                DoubleContentRow(
                    leftContent = {
                        Text(
                            text = "Тема"
                        )
                    },
                    rightContent = {
                        Text(
                            text = "Светлая|Тёмная|Системная"
                        )
                    }
                )
            }
        )
        SettingsSection(
            titleIcon = Icons.Filled.AutoFixHigh,
            titleText = R.string.selection_search_algorithm,
            content = {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                ) {
                    state.algorithms.forEach { type ->
                        SegmentedButton(
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(0.dp, Color.Transparent),
                            colors = SegmentedButtonDefaults.colors().copy(
                                activeContainerColor = Color.Blue.copy(alpha = 0.7f),
                                inactiveContainerColor = Color.Transparent
                            ),
                            onClick = { /*TODO event */ },
                            selected = type.isSelected,
                            icon = { },
                            label = {
                                Text(
                                    text = stringResource(type.title!!),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = if (type.isSelected) Color.White else Color.Black
                                )
                            }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 20.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    text = state.algorithms
                        .firstOrNull { it.isSelected }?.description
                        ?.let {
                            stringResource(it)
                        } ?: ""
                )
            }
        )
        SettingsSection(
            titleIcon = Icons.Filled.Shield,
            titleText = R.string.selection_util,
            content = {
                state.deletionTypes.forEach { algorithm ->
                    UtilizationRadioButton(
                        icon = algorithm.icon!!,
                        iconColor = algorithm.color!!,
                        title = stringResource(algorithm.title!!),
                        description = stringResource(algorithm.description!!),
                        selected = algorithm.isSelected,
                        onClick = {}
                    )
                }
            }
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = itemsRoundedShape,
            onClick = {}
        ) {
            Icon(imageVector = Icons.Filled.Replay, contentDescription = null)
            Text(stringResource(R.string.button_rescan))
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = "${state.version} • ${state.currentYear}"
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
private fun SettingsTabContentPreview() {
    PhotoCleanerTheme {
        val state = SettingsTabState(
            version = "0.0.0",
            themes = AppTheme.Light.mapThemes(),
            languages = Language.Ru.mapLanguages(),
            algorithms = WorkAlgorithm.DayMoth.mapAlgorithms(),
            deletionTypes = DeletionType.Instant.mapDeletionTypes()
        )
        SettingsTabContent(state) {}
    }
}