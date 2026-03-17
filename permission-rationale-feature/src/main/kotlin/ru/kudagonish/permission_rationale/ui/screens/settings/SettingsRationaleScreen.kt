package ru.kudagonish.permission_rationale.ui.screens.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import ru.kudagonish.permission_rationale.ui.screens.permissions.PermissionRationaleViewModel
import ru.kudagonish.permission_rationale.ui.screens.permissions.PermissionRationaleViewModel.Action
import ru.kudagonish.permission_rationale.ui.screens.permissions.PermissionRationaleViewModel.Event
import ru.kudagonish.permission_rationale.ui.screens.settings.content.SettingsRationaleContent
import ru.kudagonish.permission_rationale.util.checkPermissionGranted

@Composable
internal fun SettingsRationaleScreen(
    viewmodel: PermissionRationaleViewModel,
    onNavigateToMainScreen: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.action.collect { action ->
            when(action){
                Action.NavigateToMainScreen -> onNavigateToMainScreen()
                Action.NavigateToPhoneSettings -> {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts(
                            "package",
                            context.packageName,
                            null
                        )
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    LifecycleResumeEffect(Unit) {
        if (context.checkPermissionGranted()) {
            viewmodel.sendEvent(Event.OnNavigateToMainScreen)
        }
        onPauseOrDispose { }
    }

    SettingsRationaleContent(
        onNavigateToSettings = { viewmodel.sendEvent(Event.OnNavigateToPhoneSettings) }
    )
}