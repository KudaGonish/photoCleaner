package ru.kudagonish.permission_rationale.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.permission_rationale.ui.navigation.PermissionsNavigation.PermissionRationale
import ru.kudagonish.permission_rationale.ui.navigation.PermissionsNavigation.SettingsRationale
import ru.kudagonish.permission_rationale.ui.screens.permissions.PermissionRationaleScreen
import ru.kudagonish.permission_rationale.ui.screens.settings.SettingsRationaleScreen
import ru.kudagonish.permission_rationale.util.PermissionStatus

fun NavGraphBuilder.registerPermissionsScreens(
    navController: NavController,
    permissionStatus: PermissionStatus
) {
    val startDestination: Any = when (permissionStatus) {
        PermissionStatus.PermanentlyDenied -> SettingsRationale
        else -> PermissionRationale
    }

    this.navigation<PermissionsNavigation.Route>(startDestination) {
        composable<PermissionRationale> { backstack ->
            val parentBackstack = backstack.requireParentBackStackEntry(navController)
            PermissionRationaleScreen(
                viewmodel = koinViewModel(viewModelStoreOwner = parentBackstack),
                onNavigateToMainScreen = {
                    navController.navigate(Main) {
                        popUpTo(route = PermissionsNavigation.Route){
                            inclusive = true
                        }
                    }
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

        composable<SettingsRationale> { backstack ->
            val parentBackstack = backstack.requireParentBackStackEntry(navController)
            SettingsRationaleScreen(
                viewmodel = koinViewModel(viewModelStoreOwner = parentBackstack),
                onNavigateToMainScreen = {
                    navController.navigate(Main) {
                        popUpTo(route = PermissionsNavigation.Route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Serializable
data object Main

@Composable
fun NavBackStackEntry.requireParentBackStackEntry(
    navController: NavController
): NavBackStackEntry = remember(this) {
    val parentRoute = destination.parent?.route ?: error("current destination has no parent")

    navController.getBackStackEntry(parentRoute)
}