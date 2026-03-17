package ru.kudagonish.datastore.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.kudagonish.datastore.DataStoreManager
import ru.kudagonish.datastore.DataStoreManagerImpl

val dataStoreModule = module {
    single<DataStoreManager> { DataStoreManagerImpl(androidContext()) }
}
