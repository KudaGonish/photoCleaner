package ru.kudagonish.permission_rationale.screens.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.kudagonish.permission_rationale.screens.navigation.PermissionsScreens.PermissionRationale
import ru.kudagonish.permission_rationale.screens.navigation.PermissionsScreens.SettingsRationale
import ru.kudagonish.permission_rationale.screens.permissions.PermissionRationaleScreen
import ru.kudagonish.permission_rationale.screens.settings.SettingsRationaleScreen

fun NavGraphBuilder.registerPermissionsScreens(navController: NavController) {
    composable<PermissionRationale> {
        PermissionRationaleScreen(
            onNavigateToMainScreen = {
                navController.navigate(SettingsRationale)
            },
            onNavigateToSettingsInstructionScreen = {
                navController.navigate(SettingsRationale) {
                    this.popUpTo(PermissionRationale) {
                        this.inclusive = true
                    }
                }
            }
        )
    }

    composable<SettingsRationale> {
        SettingsRationaleScreen(
            onNavigateToMainScreen = {}
        )
    }
}