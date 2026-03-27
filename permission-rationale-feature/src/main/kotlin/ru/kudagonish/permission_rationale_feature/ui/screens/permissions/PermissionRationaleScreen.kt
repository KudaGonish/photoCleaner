package ru.kudagonish.permission_rationale_feature.ui.screens.permissions

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel
import ru.kudagonish.permission_rationale_feature.ui.screens.PermissionsViewModel.Event
import ru.kudagonish.permission_rationale_feature.ui.screens.permissions.content.PermissionRationaleContent
import ru.kudagonish.permission_rationale_feature.util.callback
import ru.kudagonish.core.galleryPermission
import ru.kudagonish.core.permissionRequestRationale

@Composable
internal fun PermissionRationaleScreen(
    onNavigateToMainScreen: callback,
    onNavigateToSettingsInstructionScreen: callback,
    viewmodel: PermissionsViewModel
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