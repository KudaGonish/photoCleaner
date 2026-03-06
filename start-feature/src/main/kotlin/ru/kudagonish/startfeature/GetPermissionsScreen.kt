package ru.kudagonish.startfeature

import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GetPermissionsScreen() {
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
            modifier = Modifier
                .size(240.dp, 64.dp)
                .background(MaterialTheme.colorScheme.primary),
            textAlign = TextAlign.Center,
            text = "Тут хочется что бы как на айфоне писалось \"привет\""
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Тут должен быть текст по типу Ну вот для того что бы приложение работало " +
                    "корректно, нужно выдать разрешение к галерее с пунктом \"Разрешить ко всем\"" +
                    "\n но с красивым шрифтом и чуть более большим размером шрифта",
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))
        Button(
            modifier = Modifier
                .size(256.dp, 52.dp),
            onClick = {}
        ) { Text(stringResource(R.string.button_grant_premission)) }
    }
}

@Preview(showBackground = true)
@Composable
fun GetPermissionsScreenPreview() {
    GetPermissionsScreen()
}