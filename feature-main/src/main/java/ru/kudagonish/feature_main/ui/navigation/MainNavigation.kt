package ru.kudagonish.feature_main.ui.navigation

import kotlinx.serialization.Serializable

sealed interface MainNavigation {
    @Serializable
    data object MainScreen : MainNavigation
}