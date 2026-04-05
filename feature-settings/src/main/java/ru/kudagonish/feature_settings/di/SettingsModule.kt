package ru.kudagonish.feature_settings.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.feature_settings.data.SettingsRepositoryImpl
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.feature_settings.ui.tab.viewModel.SettingsTabViewModel

val settingsModule = module {
    factoryOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    viewModelOf(::SettingsTabViewModel)
}