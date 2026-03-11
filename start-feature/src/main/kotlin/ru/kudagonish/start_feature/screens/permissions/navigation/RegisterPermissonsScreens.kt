package ru.kudagonish.start_feature.screens.permissions.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.kudagonish.start_feature.screens.permissions.PermissionRationaleScreen
import ru.kudagonish.start_feature.screens.permissions.navigation.PermissionsScreens.PermissionRationale
import ru.kudagonish.start_feature.screens.permissions.navigation.PermissionsScreens.SettingsRationale

fun NavGraphBuilder.registerPermissionsScreens(navController: NavController) {
    composable<PermissionRationale> {
        PermissionRationaleScreen(
            onNavigateToMainScreen = {
               // navController.navigate(Any())
            },
            onNavigateToSettingsInstructionScreen = {
                navController.navigate(SettingsRationale)
            }
        )
    }

    composable<SettingsRationale> {
        Box {
            Text("SettingsRationale")
        }
    }
}