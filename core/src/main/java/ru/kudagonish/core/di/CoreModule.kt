package ru.kudagonish.core.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kudagonish.core.ContextProvider
import ru.kudagonish.core.ContextProviderImpl

val coreModule = module {
    single<ContextProvider> { ContextProviderImpl(androidContext()) }
}