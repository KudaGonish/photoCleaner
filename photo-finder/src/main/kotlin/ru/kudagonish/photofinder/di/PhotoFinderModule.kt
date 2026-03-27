package ru.kudagonish.photofinder.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.kudagonish.photofinder.data.dataSource.db.ScannedPhotoDataSource
import ru.kudagonish.photofinder.data.dataSource.db.local.ScannedPhotoDataSourceImpl
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSourceImpl
import ru.kudagonish.photofinder.data.repository.GalleryRepositoryImpl
import ru.kudagonish.photofinder.domain.GalleryRepository

val photoFinderModule = module {
    factoryOf(::GalleryDataSourceImpl) bind GalleryDataSource::class
    factoryOf(::ScannedPhotoDataSourceImpl) bind ScannedPhotoDataSource::class
    factoryOf(::GalleryRepositoryImpl) bind GalleryRepository::class
}
