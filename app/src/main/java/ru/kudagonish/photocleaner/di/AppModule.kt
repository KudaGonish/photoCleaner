package ru.kudagonish.photocleaner.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.kudagonish.photocleaner.MainViewModel

val appModule = module {
    viewModelOf(::MainViewModel)
}