package ru.kudagonish.photocleaner.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.photocleaner.data.AppRepositoryImpl
import ru.kudagonish.photocleaner.domain.AppRepository
import ru.kudagonish.photocleaner.ui.activity.viewModel.MainViewModel

val appModule = module {
    factoryOf(::AppRepositoryImpl) bind AppRepository::class
    viewModelOf(::MainViewModel)
}