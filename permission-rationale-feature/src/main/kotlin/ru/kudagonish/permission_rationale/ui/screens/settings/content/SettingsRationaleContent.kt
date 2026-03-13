package ru.kudagonish.permission_rationale.ui.screens.settings.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kudagonish.core_ui.elements.containers.blurBlobs.BlurBlobsContainer
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.permission_rationale.R
import ru.kudagonish.permission_rationale.ui.screens.settings.ui.StepItem
import ru.kudagonish.permission_rationale.util.callback

@Composable
internal fun SettingsRationaleContent(
    onNavigateToSettings: callback
) {
    BlurBlobsContainer(background = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Icon(
                imageVector = Icons.Default.ImageSearch,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFF111827)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.settings_rationale_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.settings_rationale_description),
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = 24.sp,
                color = Color(0xFF4B5563),
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(48.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StepItem(
                    number = stringResource(R.string.step_1),
                    text = stringResource(R.string.settings_rationale_step_1)
                )
                StepItem(
                    number = stringResource(R.string.step_2),
                    text = stringResource(R.string.settings_rationale_step_2)
                )
                StepItem(
                    number = stringResource(R.string.step_3),
                    text = stringResource(R.string.settings_rationale_step_3)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onNavigateToSettings,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF111827),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.settings_rationale_button),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Composable
private fun SettingsRationalePreview() {
    PhotoCleanerTheme {
        SettingsRationaleContent(onNavigateToSettings = {})
    }
}