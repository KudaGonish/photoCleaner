package ru.kudagonish.core

sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data object NotGranted : PermissionStatus
    data object PermanentlyDenied : PermissionStatus
}