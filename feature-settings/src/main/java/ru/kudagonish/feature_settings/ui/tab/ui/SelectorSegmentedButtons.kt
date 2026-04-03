package ru.kudagonish.feature_settings.ui.tab.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.ui.tab.viewModel.SelectionItem

@Composable
internal fun SelectorSegmentedButtons(
    algorithms: ImmutableList<SelectionItem<WorkAlgorithm>>,
    onChangeValue: (WorkAlgorithm) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        algorithms.forEach { type ->
            SegmentedButton(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(0.dp, Color.Transparent),
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = MaterialTheme.colorScheme.secondary,
                    inactiveContainerColor = Color.Transparent
                ),
                onClick = { onChangeValue(type.setting) },
                selected = type.isSelected,
                icon = { },
                label = {
                    Text(
                        text = stringResource(type.title!!),
                        style = MaterialTheme.typography.titleSmall,
                        color = if (type.isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            )
        }
    }

    Spacer(Modifier.height(16.dp))

    val description = algorithms
        .firstOrNull { it.isSelected }?.description
        ?.let { stringResource(it) } ?: ""

    AnimatedContent(
        targetState = description,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) +
                    scaleIn(initialScale = 0.95f) togetherWith fadeOut(animationSpec = tween(200))
        },
        label = ""
    ) { targetLabel ->
        Text(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            style = MaterialTheme.typography.bodySmall,
            text = targetLabel
        )
    }
}