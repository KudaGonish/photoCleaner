package ru.kudagonish.permission_rationale.ui.navigation

import kotlinx.serialization.Serializable

sealed interface PermissionsScreens {

    @Serializable
    data object PermissionRationale : PermissionsScreens

    @Serializable
    data object SettingsRationale : PermissionsScreens
}