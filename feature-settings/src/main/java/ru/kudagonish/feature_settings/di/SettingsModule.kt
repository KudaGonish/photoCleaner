package ru.kudagonish.feature_settings.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel

val settingsModule = module {
    viewModelOf(::SettingsTabViewModel)
}