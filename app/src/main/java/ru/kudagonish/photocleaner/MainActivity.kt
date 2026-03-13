package ru.kudagonish.photocleaner

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import org.koin.android.ext.android.inject
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.permission_rationale.ui.navigation.PermissionsScreens
import ru.kudagonish.permission_rationale.ui.navigation.registerPermissionsScreens
import ru.kudagonish.permission_rationale.util.PermissionStatus
import ru.kudagonish.permission_rationale.util.getPermissionStatus
import ru.kudagonish.photocleaner.splash.SplashBlurBlobs
import ru.kudagonish.photofinder.GalleryScanner

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
                    val startDestination = when (permissionStatus) {
                        PermissionStatus.Granted -> PermissionsScreens.PermissionRationale
                        PermissionStatus.NotGranted -> PermissionsScreens.PermissionRationale
                        PermissionStatus.PermanentlyDenied -> PermissionsScreens.SettingsRationale
                    }

                    NavHost(navController, startDestination = startDestination) {
                        registerPermissionsScreens(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scanner = remember { GalleryScanner(context) }
    var firstImageUri by remember { mutableStateOf<String?>(null) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Hello $name!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val images = scanner.getImages()
            if (images.isNotEmpty()) {
                firstImageUri = images.first().uri
            }
            images.forEach { photo ->
                Log.d("PhotoFinder", "Uri: ${photo.uri}, Date: ${photo.dateAdded}")
            }
            Log.d("PhotoFinder", "Total images found: ${images.size}")
        }) {
            Text("Scan Gallery")
        }

        Spacer(modifier = Modifier.height(16.dp))

        firstImageUri?.let { uri ->
            Text(text = "First photo:")
            AsyncImage(
                model = uri,
                contentDescription = "First photo from gallery",
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoCleanerTheme {
        Greeting("Android")
    }
}
