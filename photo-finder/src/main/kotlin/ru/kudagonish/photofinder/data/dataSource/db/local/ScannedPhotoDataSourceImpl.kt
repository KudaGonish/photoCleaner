package ru.kudagonish.photofinder.data.dataSource.db.local

import ru.kudagonish.photofinder.data.dataSource.db.ScannedPhotoDataSource
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

internal class ScannedPhotoDataSourceImpl(
    private val dao: GalleryInformationDao
) : ScannedPhotoDataSource {
    override suspend fun insertScannedPhotos(list: List<GalleryInformationEntity>) {
        dao.insertPhotos(list)
    }
}