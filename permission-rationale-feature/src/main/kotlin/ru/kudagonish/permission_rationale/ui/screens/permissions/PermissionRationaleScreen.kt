package ru.kudagonish.permission_rationale.ui.screens.permissions

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import ru.kudagonish.permission_rationale.ui.screens.permissions.PermissionRationaleViewModel.Event
import ru.kudagonish.permission_rationale.ui.screens.permissions.content.PermissionRationaleContent
import ru.kudagonish.permission_rationale.util.callback
import ru.kudagonish.permission_rationale.util.galleryPermission
import ru.kudagonish.permission_rationale.util.permissionRequestRationale

@Composable
internal fun PermissionRationaleScreen(
    onNavigateToMainScreen: callback,
    onNavigateToSettingsInstructionScreen: callback,
    viewmodel: PermissionRationaleViewModel
) {
    val activity = requireNotNull(LocalActivity.current)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            viewmodel.sendEvent(Event.OnSuccessRequestPermission)
            onNavigateToMainScreen()
        } else {
            viewmodel.sendEvent(Event.OnFailureRequestPermission)
            val needNavigateToSettings = !activity.permissionRequestRationale()
            if (needNavigateToSettings) onNavigateToSettingsInstructionScreen()
        }
    }

    PermissionRationaleContent(
        onGrantPermissionClick = { launcher.launch(galleryPermission) }
    )
}