package ru.kudagonish.photofinder.data.dataSource.db.local

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

internal class ImagesLocalDataSourceImpl(
    private val galleryInformationDao: GalleryInformationDao
) : ImagesLocalDataSource {
    override fun getPhotos(): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos()

    override fun getPhotos(date: Long): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos(date)
}