package ru.kudagonish.permission_rationale_feature.ui.navigation

import kotlinx.serialization.Serializable

sealed interface PermissionsNavigation {
    @Serializable
    data object Route : PermissionsNavigation

    @Serializable
    data object PermissionRationaleScreen : PermissionsNavigation

    @Serializable
    data object SettingsRationaleScreen : PermissionsNavigation
}