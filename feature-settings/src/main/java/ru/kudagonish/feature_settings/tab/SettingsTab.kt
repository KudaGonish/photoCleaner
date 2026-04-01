package ru.kudagonish.feature_settings.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoFixHigh
import androidx.compose.material.icons.outlined.ColorLens
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_settings.R

private val itemsRoundedShape = RoundedCornerShape(28.dp)

@Composable
internal fun SettingsTab() {
    SettingsTabContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsTabContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tab_settings),
                        style = MaterialTheme.typography.headlineSmall.copy(fontFamily = null),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            StatisticCard()
            SettingsSection(
                titleIcon = Icons.Outlined.ColorLens,
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
                titleIcon = Icons.Outlined.AutoFixHigh,
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
                            SegmentedButton(
                                modifier = Modifier.weight(1f).height(40.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(0.dp, Color.Transparent),
                                colors = SegmentedButtonDefaults.colors().copy(
                                    activeContainerColor = Color.Blue.copy(alpha = 0.7f),
                                    inactiveContainerColor = Color.Transparent
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                icon = { },
                                label = { Text(label) }
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 20.dp),
                        text = "Какой то текст с описанием работы режимов ляляля пару строчек надо сделать"
                    )
                }
            )
        }
    }
}

@Composable
internal fun DoubleContentRow(
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leftContent()
        rightContent()
    }
}

@Composable
internal fun SettingsSection(
    titleIcon: ImageVector,
    titleText: Int,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = titleIcon,
                contentDescription = null,
                tint = Color.Gray
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(titleText).uppercase(),
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium.copy(fontFamily = null),
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = itemsRoundedShape,
                    shadow = Shadow(
                        radius = 3.dp,
                        color = Color.Black,
                        alpha = 0.2f
                    )
                )
                .clip(itemsRoundedShape)
                .background(Color.White),
            content = content
        )
    }
}

@Composable
internal fun StatisticCard() {
    Box(
        Modifier
            .clip(itemsRoundedShape)
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.Blue.copy(alpha = 0.7f))
    )
}

@Preview(showBackground = true, locale = "ru")
@Composable
private fun SettingsTabPreview() {
    PhotoCleanerTheme {
        SettingsTab()
    }
}
