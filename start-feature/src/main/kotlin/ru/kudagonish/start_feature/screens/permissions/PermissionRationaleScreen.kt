package ru.kudagonish.start_feature.screens.permissions

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import ru.kudagonish.start_feature.screens.permissions.content.PermissionRationaleContent
import ru.kudagonish.start_feature.util.callback
import ru.kudagonish.start_feature.util.galleryPermission
import ru.kudagonish.start_feature.util.permissionRequestRationale

@Composable
internal fun PermissionRationaleScreen(
    onNavigateToMainScreen: callback,
    onNavigateToSettingsInstructionScreen: callback
) {
    val activity = requireNotNull(LocalActivity.current)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permissionGranted ->
        if (permissionGranted) {
            onNavigateToMainScreen()
        } else {
            val needNavigateToSettings = !activity.permissionRequestRationale()
            if (needNavigateToSettings) onNavigateToSettingsInstructionScreen()
        }
    }

    PermissionRationaleContent(
        onGrantPermissionClick = { launcher.launch(galleryPermission) }
    )
}