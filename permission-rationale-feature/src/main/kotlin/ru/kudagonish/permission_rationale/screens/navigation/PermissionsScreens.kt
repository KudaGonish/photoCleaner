package ru.kudagonish.permission_rationale.screens.navigation

import kotlinx.serialization.Serializable

sealed interface PermissionsScreens {

    @Serializable
    data object PermissionRationale : PermissionsScreens

    @Serializable
    data object SettingsRationale : PermissionsScreens
}