package ru.kudagonish.photocleaner.ui.activity

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.kudagonish.core.PermissionStatus
import ru.kudagonish.core.getPermissionStatus
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.feature_main.ui.navigation.MainNavigation
import ru.kudagonish.feature_main.ui.navigation.registerMainScreen
import ru.kudagonish.permission_rationale_feature.ui.navigation.PermissionsNavigation
import ru.kudagonish.permission_rationale_feature.ui.navigation.registerPermissionsScreens
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel
import ru.kudagonish.photocleaner.ui.splash.SplashBlurBlobs

internal fun ComponentActivity.setApplicationContent(viewModel: MainViewModel) {
    setContent {
        val state by viewModel.state.collectAsStateWithLifecycle()
        LocaleWrapper(state.language){
            PhotoCleanerTheme(darkTheme = state.theme) {
                val navController = rememberNavController()
                var showSplashAnim by rememberSaveable { mutableStateOf(true) }
                val permissionRequestCount by viewModel.requestPermissionCount.collectAsState()

                if (showSplashAnim) SplashBlurBlobs(onAnimationFinished = { showSplashAnim = false })
                else permissionRequestCount?.let { count ->
                    val permissionStatus = remember(count) {
                        getPermissionStatus(
                            this@setApplicationContent.applicationContext,
                            this@setApplicationContent as Activity,
                            count
                        )
                    }
                    val startDestination: Any = when (permissionStatus) {
                        PermissionStatus.Granted -> MainNavigation.MainScreen
                        else -> PermissionsNavigation.Route
                    }

                    NavHost(
                        modifier = Modifier
                            .safeContentPadding()
                            .background(MaterialTheme.colorScheme.background),
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        registerPermissionsScreens(navController, permissionStatus)
                        registerMainScreen()
                    }
                }
            }
        }
    }
}