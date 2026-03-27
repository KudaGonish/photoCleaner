package ru.kudagonish.photofinder.data.dataSource.db

import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

internal interface ScannedPhotoDataSource {

    suspend fun insertScannedPhotos(list: List<GalleryInformationEntity>)

}