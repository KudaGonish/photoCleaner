package ru.kudagonish.permission_rationale_feature.ui.screens.permissions.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kudagonish.core_ui.elements.containers.blurBlobs.BlurBlobsContainer
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.core_ui.theme.helloTitleFontStyle
import ru.kudagonish.permission_rationale_feature.R
import ru.kudagonish.permission_rationale_feature.ui.screens.permissions.ui.getTweenSpec

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun PermissionRationaleContent(
    onGrantPermissionClick: () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    val description = buildAnnotatedString {
        val strings = stringArrayResource(R.array.permissions_description)
        if (strings.size >= 2) {
            append(strings[0])
            append(" ")
            withStyle(SpanStyle( color = Color(0xFF111827))) {
                append(strings[1])
            }
        }
    }

    LaunchedEffect(Unit) {
        isVisible = true
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
            AnimatedVisibility(
                visible = isVisible,
                enter = scaleIn(animationSpec = getTweenSpec(HELLO_DELAY)) +
                        fadeIn(animationSpec = getTweenSpec(HELLO_DELAY))
            ) {
                Text(
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    style = helloTitleFontStyle,
                    color = Color(0xFF111827),
                    text = stringResource(R.string.hello)
                )
            }
            Spacer(Modifier.weight(0.5f))
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = getTweenSpec(DESCRIPTION_DELAY)) +
                        expandVertically(animationSpec = getTweenSpec(DESCRIPTION_DELAY))
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    color = Color(0xFF4B5563),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(Modifier.weight(1f))
            AnimatedVisibility(
                visible = isVisible,
                enter = expandVertically(
                    animationSpec = getTweenSpec(BUTTON_DELAY),
                    expandFrom = Alignment.Top
                ) + fadeIn(animationSpec = getTweenSpec(BUTTON_DELAY))
            ) {
                Button(
                    modifier = Modifier
                        .size(256.dp, 56.dp),
                    onClick = onGrantPermissionClick,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF111827),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    ),
                    content = {
                        Text(
                            text = stringResource(R.string.button_grant_permission),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        )
                    }
                )
            }
        }
    }
}

private const val HELLO_DELAY = 300
private const val DESCRIPTION_DELAY = HELLO_DELAY + 600
private const val BUTTON_DELAY = DESCRIPTION_DELAY + 700

@Preview(showBackground = true, locale = "RU")
@Composable
private fun PermissionRationaleScreenPreview() {
    PhotoCleanerTheme {
        PermissionRationaleContent(
            onGrantPermissionClick = { }
        )
    }
}