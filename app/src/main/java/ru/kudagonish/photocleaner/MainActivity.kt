package ru.kudagonish.photocleaner

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.permission_rationale_feature.ui.navigation.Main
import ru.kudagonish.permission_rationale_feature.ui.navigation.PermissionsNavigation
import ru.kudagonish.permission_rationale_feature.ui.navigation.registerPermissionsScreens
import ru.kudagonish.core.PermissionStatus
import ru.kudagonish.core.getPermissionStatus
import ru.kudagonish.photocleaner.splash.SplashBlurBlobs

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        splashScreen.setKeepOnScreenCondition { viewModel.requestPermissionCount.value == null }

        setContent {
            PhotoCleanerTheme {
                val navController = rememberNavController()
                var showSplashAnim by remember { mutableStateOf(true) }
                val permissionRequestCount by viewModel.requestPermissionCount.collectAsState()

                if (showSplashAnim) {
                    SplashBlurBlobs(onAnimationFinished = { showSplashAnim = false })
                } else {
                    val permissionStatus = remember(permissionRequestCount != null) {
                        getPermissionStatus(
                            this.applicationContext,
                            this as Activity,
                            permissionRequestCount!!
                        )
                    }
                    val startDestination: Any = when (permissionStatus) {
                        PermissionStatus.Granted -> Main
                        else -> PermissionsNavigation.Route
                    }

                    NavHost(navController, startDestination = startDestination) {
                        registerPermissionsScreens(navController, permissionStatus)
                        composable<Main> {}
                    }
                }
            }
        }
    }
}