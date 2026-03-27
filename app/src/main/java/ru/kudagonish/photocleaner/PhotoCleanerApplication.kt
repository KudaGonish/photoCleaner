package ru.kudagonish.photocleaner

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.kudagonish.core.di.coreModule
import ru.kudagonish.datastore.di.dataStoreModule
import ru.kudagonish.permission_rationale.di.permissionRationaleModule
import ru.kudagonish.photocleaner.di.appModule
import ru.kudagonish.photofinder.di.photoFinderModule

class PhotoCleanerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotoCleanerApplication)
            modules(
                permissionRationaleModule,
                dataStoreModule,
                coreModule,
                photoFinderModule,
                appModule
            )
        }
    }
}