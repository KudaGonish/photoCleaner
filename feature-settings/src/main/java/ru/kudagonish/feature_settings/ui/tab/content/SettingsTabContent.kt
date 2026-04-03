package ru.kudagonish.feature_settings.ui.tab.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import ru.kudagonish.feature_settings.ui.tab.ui.DropdownMenu
import ru.kudagonish.feature_settings.ui.tab.ui.RescanButton
import ru.kudagonish.feature_settings.ui.tab.ui.SelectorSegmentedButtons
import ru.kudagonish.feature_settings.ui.tab.ui.SettingsSection
import ru.kudagonish.feature_settings.ui.tab.ui.StatisticCard
import ru.kudagonish.feature_settings.ui.tab.ui.ThemeSelector
import ru.kudagonish.feature_settings.ui.tab.ui.UtilizationRadioButton
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
//        StatisticCard()
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
                            style = MaterialTheme.typography.titleSmall
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
                            style = MaterialTheme.typography.titleSmall
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
        RescanButton()
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