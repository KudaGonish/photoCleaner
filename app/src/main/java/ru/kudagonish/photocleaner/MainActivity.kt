package ru.kudagonish.photocleaner

import android.content.pm.PackageManager
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import ru.kudagonish.core_ui.theme.PhotoCleanerTheme
import ru.kudagonish.photofinder.GalleryScanner
import ru.kudagonish.start_feature.screens.permissions.GetPermissionsScreen
import ru.kudagonish.start_feature.util.galleryPermission

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoCleanerTheme {
                val needsRequestPermission = galleryPermission.any {
                    ContextCompat.checkSelfPermission(
                        LocalContext.current,
                        galleryPermission
                    ) != PackageManager.PERMISSION_GRANTED
                }


                //todo navhost if needsRequestPermission GetPermissionsScreen else main screen

                GetPermissionsScreen(
                    onNavigateToMainScreen = {
                        Log.d("TAG", "GetPermissionsScreenPreview: permissions granted")
                    },
                    onNavigateToSettingsInstructionScreen = {
                        Log.d("TAG", "GetPermissionsScreenPreview: need show settings")
                    }
                )
                /*
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }*/
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
