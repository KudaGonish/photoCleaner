package ru.kudagonish.feature_settings.ui.tab.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kudagonish.feature_settings.R

@Composable
internal fun RescanButton() {
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
}