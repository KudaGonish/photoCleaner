package ru.kudagonish.start_feature.screens.permissions.navigation

import kotlinx.serialization.Serializable

sealed interface PermissionsScreens {

    @Serializable
    data object PermissionRationale : PermissionsScreens

    @Serializable
    data object SettingsRationale : PermissionsScreens
}