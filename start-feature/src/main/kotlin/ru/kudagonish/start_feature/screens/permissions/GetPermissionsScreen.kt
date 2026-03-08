package ru.kudagonish.start_feature.screens.permissions

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import ru.kudagonish.start_feature.screens.permissions.content.GetPermissionsContent
import ru.kudagonish.start_feature.util.callback
import ru.kudagonish.start_feature.util.galleryPermission

@Composable
fun GetPermissionsScreen(
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
            val needNavigateTpSettings =
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, galleryPermission)

            if (needNavigateTpSettings) onNavigateToSettingsInstructionScreen()
        }
    }

    GetPermissionsContent(
        onGrantPermissionClick = { launcher.launch(galleryPermission) }
    )
}