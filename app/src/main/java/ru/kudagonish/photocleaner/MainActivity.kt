package ru.kudagonish.photocleaner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import ru.kudagonish.photocleaner.ui.theme.PhotoCleanerTheme
import ru.kudagonish.photofinder.GalleryScanner

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoCleanerTheme {
                val context = LocalContext.current
                val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                val launcher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    val allGranted = permissions.values.all { it }
                    if (allGranted) {
                        Log.d("MainActivity", "Permissions granted")
                    } else {
                        Log.d("MainActivity", "Permissions denied")
                    }
                }

                LaunchedEffect(Unit) {
                    val needsRequest = permissionsToRequest.any {
                        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
                    }
                    if (needsRequest) {
                        launcher.launch(permissionsToRequest)
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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
