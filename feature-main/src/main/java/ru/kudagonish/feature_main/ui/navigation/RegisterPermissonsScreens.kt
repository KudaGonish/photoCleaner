package ru.kudagonish.feature_main.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.kudagonish.feature_main.ui.MainScreen

fun NavGraphBuilder.registerMainScreen() {
    composable<MainNavigation.MainScreen> { MainScreen() }
}