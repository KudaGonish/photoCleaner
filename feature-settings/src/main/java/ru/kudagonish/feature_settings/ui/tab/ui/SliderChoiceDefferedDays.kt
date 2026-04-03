package ru.kudagonish.feature_settings.ui.tab.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.R
import kotlin.math.roundToInt

@Composable
internal fun SliderChoiceDefferedDays(days: Int, onSetValue: (DeletionType) -> Unit) {
    var sliderValue by remember { mutableIntStateOf(days) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .padding(start = 24.dp, end = 32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.selection_util_slider_self_life),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
            Text(
                text = pluralStringResource(
                    id = R.plurals.selection_util_slider_days,
                    count = sliderValue,
                    sliderValue
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
        Spacer(Modifier.height(4.dp))
        Slider(
            modifier = Modifier.height(40.dp),
            value = sliderValue.toFloat(),
            onValueChange = {
                sliderValue = it.roundToInt()
            },
            onValueChangeFinished = {
                onSetValue(DeletionType.Deffered(sliderValue))
            },
            valueRange = 1f..14f,
            colors = SliderDefaults.colors().copy(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                activeTickColor = MaterialTheme.colorScheme.onSecondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                inactiveTickColor = MaterialTheme.colorScheme.onSecondaryContainer
            ),
            steps = 12
        )
    }
}