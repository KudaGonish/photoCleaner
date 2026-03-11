package ru.kudagonish.start_feature.util

sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data object NotGranted : PermissionStatus
    data object PermanentlyDenied : PermissionStatus
}