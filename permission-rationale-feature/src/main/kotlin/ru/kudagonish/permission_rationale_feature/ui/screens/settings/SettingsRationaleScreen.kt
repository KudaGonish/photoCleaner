package ru.kudagonish.permission_rationale_feature.ui.screens.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel.Action
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel.Event
import ru.kudagonish.permission_rationale_feature.ui.screens.settings.content.SettingsRationaleContent
import ru.kudagonish.core.checkPermissionGranted

@Composable
internal fun SettingsRationaleScreen(
    viewmodel: PermissionsViewModel,
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