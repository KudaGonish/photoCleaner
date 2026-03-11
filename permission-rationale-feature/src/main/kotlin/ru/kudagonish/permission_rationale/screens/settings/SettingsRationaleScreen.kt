package ru.kudagonish.permission_rationale.screens.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import ru.kudagonish.permission_rationale.screens.settings.content.SettingsRationaleContent
import ru.kudagonish.permission_rationale.util.checkPermissionGranted

@Composable
internal fun SettingsRationaleScreen(
    onNavigateToMainScreen: () -> Unit
) {
    val context = LocalContext.current

    LifecycleResumeEffect(Unit) {
        if (context.checkPermissionGranted()) {
            onNavigateToMainScreen()
        }
        onPauseOrDispose { }
    }

    SettingsRationaleContent(
        onNavigateToSettings = {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
            }
            context.startActivity(intent)
        }
    )
}