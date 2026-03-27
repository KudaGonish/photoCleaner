package ru.kudagonish.photocleaner

import android.app.Application
import androidx.work.Configuration
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import ru.kudagonish.core.di.coreModule
import ru.kudagonish.datastore.di.dataStoreModule
import ru.kudagonish.permission_rationale_feature.di.permissionRationaleModule
import ru.kudagonish.photocleaner.di.appModule
import ru.kudagonish.photofinder.di.photoFinderModule
import ru.kudagonish.photofinder.worker.SyncGalleryWorker

class PhotoCleanerApplication : Application(), Configuration.Provider {

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(getKoin().get<KoinWorkerFactory>())
            .build()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotoCleanerApplication)
            workManagerFactory()
            modules(
                permissionRationaleModule,
                dataStoreModule,
                coreModule,
                photoFinderModule,
                appModule
            )
        }
        SyncGalleryWorker.schedulePeriodicWorkRequest(this)
    }
}