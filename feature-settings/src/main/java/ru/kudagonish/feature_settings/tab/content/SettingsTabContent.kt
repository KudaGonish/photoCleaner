package ru.kudagonish.feature_settings.tab.content

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
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_settings.R
import ru.kudagonish.feature_settings.tab.ui.DoubleContentRow
import ru.kudagonish.feature_settings.tab.ui.SettingsSection
import ru.kudagonish.feature_settings.tab.ui.StatisticCard
import ru.kudagonish.feature_settings.tab.ui.UtilizationRadioButton
import ru.kudagonish.feature_settings.tab.ui.itemsRoundedShape
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsTabContent() {
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
            titleText = R.string.selection_theme,
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
                var selectedIndex by remember { mutableIntStateOf(0) }
                val options = listOf("Воспоминания", "Уборка")
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray.copy(alpha = 0.2f))
                ) {
                    options.forEachIndexed { index, label ->
                        val isSelected = selectedIndex == index
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
                            onClick = { selectedIndex = index },
                            selected = isSelected,
                            icon = { },
                            label = {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = if (isSelected) Color.White else Color.Black
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
                    text = "Какой то текст с описанием работы режимов ляляля пару строчек надо сделать"
                )
            }
        )
        SettingsSection(
            titleIcon = Icons.Filled.Shield,
            titleText = R.string.selection_utilization,
            content = {
                UtilizationRadioButton(
                    icon = Icons.Outlined.Bolt,
                    iconColor = Color.Red,
                    title = stringResource(R.string.utilization_instantly),
                    description = "Какое-то описание",
                    selected = true,
                    onClick = {}
                )
                UtilizationRadioButton(
                    icon = Icons.Outlined.Schedule,
                    iconColor = Color.Green,
                    title = stringResource(R.string.utilization_deffered),
                    description = "Какое-то описание",
                    selected = false,
                    onClick = {}
                )
                UtilizationRadioButton(
                    icon = Icons.Outlined.Delete,
                    iconColor = Color.Blue,
                    title = stringResource(R.string.utilization_system_basket),
                    description = "Какое-то описание",
                    selected = false,
                    onClick = {}
                )
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
            Text("Просканировать заново")
        }
        val timezone = TimeZone.currentSystemDefault()
        val year = Clock.System.now().toLocalDateTime(timezone).year
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 8.dp),
            text = "Версия 0.0.1 • $year"
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
private fun SettingsTabContentPreview() {
    PhotoCleanerTheme {
        SettingsTabContent()
    }
}