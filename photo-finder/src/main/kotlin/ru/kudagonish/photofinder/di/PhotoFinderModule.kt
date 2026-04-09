package ru.kudagonish.photofinder.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.data.dataSource.db.ScannedPhotoDataSource
import ru.kudagonish.photofinder.data.dataSource.db.local.ImagesLocalDataSourceImpl
import ru.kudagonish.photofinder.data.dataSource.db.local.ScannedPhotoDataSourceImpl
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSourceImpl
import ru.kudagonish.photofinder.data.db.DATABASE_NAME
import ru.kudagonish.photofinder.data.db.PhotoDatabase
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.repository.GalleryRepositoryImpl
import ru.kudagonish.photofinder.data.repository.ImagesRepositoryImpl
import ru.kudagonish.photofinder.domain.GalleryRepository
import ru.kudagonish.photofinder.domain.ImagesRepository
import ru.kudagonish.photofinder.domain.useCase.IScanGalleryUseCase
import ru.kudagonish.photofinder.domain.useCase.ScanGalleryUseCase
import ru.kudagonish.photofinder.worker.SyncGalleryWorker

val photoFinderModule = module {
    single<PhotoDatabase> {
        Room.databaseBuilder(
            context = androidApplication().applicationContext,
            PhotoDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    factory<GalleryInformationDao> { get<PhotoDatabase>().galleryInformationDao() }

    factoryOf(::GalleryDataSourceImpl) bind GalleryDataSource::class
    factoryOf(::ScannedPhotoDataSourceImpl) bind ScannedPhotoDataSource::class
    factoryOf(::ImagesLocalDataSourceImpl) bind ImagesLocalDataSource::class

    factoryOf(::GalleryRepositoryImpl) bind GalleryRepository::class
    factoryOf(::ImagesRepositoryImpl) bind ImagesRepository::class
    factoryOf(::ScanGalleryUseCase) bind IScanGalleryUseCase::class

    workerOf(::SyncGalleryWorker)
}