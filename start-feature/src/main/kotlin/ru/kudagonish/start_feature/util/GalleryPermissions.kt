package ru.kudagonish.start_feature.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_IMAGES
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}

fun Context.checkPermissionGranted() = ContextCompat
    .checkSelfPermission(this, galleryPermission) == PackageManager.PERMISSION_GRANTED

fun Activity.permissionRequestRationale() = ActivityCompat
    .shouldShowRequestPermissionRationale(this, galleryPermission)

fun getPermissionStatus(context: Context, activity: Activity): PermissionStatus {
    return when {
        !context.checkPermissionGranted() -> PermissionStatus.NotGranted
        activity.permissionRequestRationale() -> PermissionStatus.PermanentlyDenied
        else -> PermissionStatus.Granted
    }
}