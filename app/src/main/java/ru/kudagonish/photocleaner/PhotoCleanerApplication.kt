package ru.kudagonish.photocleaner

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kudagonish.datastore.di.dataStoreModule
import ru.kudagonish.permission_rationale.di.permissionRationaleModule
import ru.kudagonish.photocleaner.di.appModule

class PhotoCleanerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotoCleanerApplication)
            modules(
                dataStoreModule,
                permissionRationaleModule,
                appModule
            )
        }
    }
}