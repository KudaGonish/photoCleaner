package ru.kudagonish.feature_settings.ui.tab.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.elements.bottomMenuPadding
import ru.kudagonish.core_ui.theme.LocalCustomColors
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.R
import ru.kudagonish.feature_settings.ui.tab.ui.DoubleContentRow
import ru.kudagonish.feature_settings.ui.tab.ui.DropdownMenu
import ru.kudagonish.feature_settings.ui.tab.ui.RescanButton
import ru.kudagonish.feature_settings.ui.tab.ui.SelectorSegmentedButtons
import ru.kudagonish.feature_settings.ui.tab.ui.SettingsSection
import ru.kudagonish.feature_settings.ui.tab.ui.SliderChoiceDefferedDays
import ru.kudagonish.feature_settings.ui.tab.ui.StatisticCard
import ru.kudagonish.feature_settings.ui.tab.ui.ThemeSelector
import ru.kudagonish.feature_settings.ui.tab.ui.UtilizationRadioButton
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabState
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabStateProvider
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsTabContent(
    state: SettingsTabState,
    sendEvent: (Event) -> Unit
) {
    if (state.isLoading) SettingsTabShimmer()
    else{
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = bottomMenuPadding),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Box(
                modifier = Modifier.padding(top = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.tab_settings),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                )
            }
            StatisticCard()
            SettingsSection(
                titleIcon = Icons.Filled.ColorLens,
                titleText = R.string.selection_ui,
                content = {
                    Spacer(Modifier.height(16.dp))
                    DoubleContentRow(
                        leftContent = {
                            Text(
                                text = stringResource(R.string.selection_ui_lang_title),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        rightContent = {
                            DropdownMenu(
                                languages = state.languages,
                                onSelectItem = { sendEvent(Event.OnChangeLanguage(it)) }
                            )
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                    DoubleContentRow(
                        leftContent = {
                            Text(
                                text = stringResource(R.string.selection_ui_theme_title),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        rightContent = {
                            ThemeSelector(
                                themes = state.themes,
                                onSelectItem = { sendEvent(Event.OnChangeTheme(it)) }
                            )
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                }
            )
            SettingsSection(
                titleIcon = Icons.Filled.AutoFixHigh,
                titleText = R.string.selection_search_algorithm,
                content = {
                    SelectorSegmentedButtons(
                        algorithms = state.algorithms,
                        onChangeValue = { sendEvent(Event.OnChangeAlgorithm(it)) }
                    )
                }
            )
            SettingsSection(
                titleIcon = Icons.Filled.Shield,
                titleText = R.string.selection_util,
                content = {
                    state.deletionTypes.forEachIndexed { index, type ->
                        val color = when (type.setting) {
                            DeletionType.Instant -> LocalCustomColors.current.instant
                            is DeletionType.Deffered -> LocalCustomColors.current.safe
                            DeletionType.SystemTrash -> LocalCustomColors.current.bin
                        }
                        Spacer(Modifier.height(16.dp))
                        UtilizationRadioButton(
                            icon = type.icon!!,
                            iconColor = color,
                            title = type.title?.asString()!!,
                            description = type.description?.asString()!!,
                            selected = type.isSelected,
                            onClick = { sendEvent(Event.OnChangeDeletionType(type.setting)) }
                        )
                        if (type.setting is DeletionType.Deffered && type.isSelected) {
                            SliderChoiceDefferedDays(
                                days = type.setting.days,
                                onSetValue = {
                                    sendEvent(Event.OnChangeDeletionType(it))
                                }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        if (index != state.deletionTypes.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            )
            RescanButton()
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .offset(y = (-16).dp)
                    .padding(bottom = 8.dp),
                text = stringResource(R.string.app_version, state.version, state.currentYear),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }


}

@Preview(showBackground = true, locale = "ru")
@Composable
private fun SettingsTabContentPreview(
    @PreviewParameter(SettingsTabStateProvider::class) state: SettingsTabState
) {
    PhotoCleanerTheme {
        SettingsTabContent(state = state, sendEvent = {})
    }
}