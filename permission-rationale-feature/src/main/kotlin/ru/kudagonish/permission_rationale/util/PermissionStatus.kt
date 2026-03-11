package ru.kudagonish.permission_rationale.util

sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data object NotGranted : PermissionStatus
    data object PermanentlyDenied : PermissionStatus
}