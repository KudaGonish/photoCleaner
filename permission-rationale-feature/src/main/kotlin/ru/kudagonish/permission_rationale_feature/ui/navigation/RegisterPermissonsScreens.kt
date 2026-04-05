package ru.kudagonish.permission_rationale_feature.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.koin.androidx.compose.koinViewModel
import ru.kudagonish.core.PermissionStatus
import ru.kudagonish.feature_main.ui.navigation.MainNavigation
import ru.kudagonish.permission_rationale_feature.ui.navigation.PermissionsNavigation.PermissionRationaleScreen
import ru.kudagonish.permission_rationale_feature.ui.navigation.PermissionsNavigation.SettingsRationaleScreen
import ru.kudagonish.permission_rationale_feature.ui.screens.permissions.PermissionRationaleScreen
import ru.kudagonish.permission_rationale_feature.ui.screens.settings.SettingsRationaleScreen

fun NavGraphBuilder.registerPermissionsScreens(
    navController: NavController,
    permissionStatus: PermissionStatus
) {
    Log.d("TAG", "registerPermissionsScreens: ${permissionStatus}")
    val startDestination: Any = when (permissionStatus) {
        PermissionStatus.PermanentlyDenied -> SettingsRationaleScreen
        else -> PermissionRationaleScreen
    }

    this.navigation<PermissionsNavigation.Route>(startDestination) {
        composable<PermissionRationaleScreen> { backstack ->
            val parentBackstack = backstack.requireParentBackStackEntry(navController)
            PermissionRationaleScreen(
                viewmodel = koinViewModel(viewModelStoreOwner = parentBackstack),
                onNavigateToMainScreen = {
                    navController.navigate(MainNavigation.MainScreen) {
                        popUpTo(route = PermissionsNavigation.Route){
                            inclusive = true
                        }
                    }
                },
                onNavigateToSettingsInstructionScreen = {
                    navController.navigate(SettingsRationaleScreen) {
                        this.popUpTo(PermissionRationaleScreen) {
                            this.inclusive = true
                        }
                    }
                }
            )
        }

        composable<SettingsRationaleScreen> { backstack ->
            val parentBackstack = backstack.requireParentBackStackEntry(navController)
            SettingsRationaleScreen(
                viewmodel = koinViewModel(viewModelStoreOwner = parentBackstack),
                onNavigateToMainScreen = {
                    navController.navigate(MainNavigation.MainScreen) {
                        popUpTo(route = PermissionsNavigation.Route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavBackStackEntry.requireParentBackStackEntry(
    navController: NavController
): NavBackStackEntry = remember(this) {
    val parentRoute = destination.parent?.route ?: error("current destination has no parent")
    navController.getBackStackEntry(parentRoute)
}