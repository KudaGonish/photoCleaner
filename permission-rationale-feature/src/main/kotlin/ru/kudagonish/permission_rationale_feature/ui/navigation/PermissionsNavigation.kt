package ru.kudagonish.permission_rationale_feature.ui.navigation

import kotlinx.serialization.Serializable

sealed interface PermissionsNavigation {
    @Serializable
    data object Route : PermissionsNavigation

    @Serializable
    data object PermissionRationale : PermissionsNavigation

    @Serializable
    data object SettingsRationale : PermissionsNavigation
}