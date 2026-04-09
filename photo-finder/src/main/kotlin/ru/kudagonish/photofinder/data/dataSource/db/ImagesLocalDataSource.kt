package ru.kudagonish.photofinder.data.dataSource.db

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

internal interface ImagesLocalDataSource {
    fun getPhotos(): Flow<List<GalleryInformationEntity>>
    fun getPhotos(date: Long): Flow<List<GalleryInformationEntity>>
}