package ru.kudagonish.start_feature.screens.permissions.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.kudagonish.core_ui.theme.helloTitleFontStyle
import ru.kudagonish.start_feature.R
import ru.kudagonish.start_feature.screens.permissions.ui.BlurBlobsContainer

@Composable
fun GetPermissionsContent(
    onGrantPermissionClick: () -> Unit
) {
    val description = buildAnnotatedString {
        val strings = stringArrayResource(R.array.permissions_description)
        if (strings.size >= 2) {
            append(strings[0])
            append(" ")
            append(strings[1])
        }
    }

    BlurBlobsContainer(background = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 128.dp,
                    bottom = 48.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                textAlign = TextAlign.Center,
                style = helloTitleFontStyle,
                color = Color.White,
                text = stringResource(R.string.hello)
            )
            Spacer(Modifier.height(64.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .size(256.dp, 52.dp),
                onClick = onGrantPermissionClick,
                content = {
                    Text(
                        text = stringResource(R.string.button_grant_permission),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, locale = "RU")
@Composable
fun GetPermissionsScreenPreview() {
    MaterialTheme {
        GetPermissionsContent(
            onGrantPermissionClick = { }
        )
    }
}