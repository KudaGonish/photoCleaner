package ru.kudagonish.datastore.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.datastore.DataStoreManager
import ru.kudagonish.datastore.DataStoreManagerImpl
import ru.kudagonish.datastore.settings.DataStoreSettings
import ru.kudagonish.datastore.settings.DataStoreSettingsImpl

val dataStoreModule = module {
    single<DataStoreManager> { DataStoreManagerImpl(androidContext()) }
    factoryOf(::DataStoreSettingsImpl) bind DataStoreSettings::class
}
