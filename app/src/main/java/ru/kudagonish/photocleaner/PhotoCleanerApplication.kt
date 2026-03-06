package ru.kudagonish.photocleaner

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhotoCleanerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhotoCleanerApplication)
        }
    }
}