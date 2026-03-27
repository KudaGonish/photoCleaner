package ru.kudagonish.photofinder.data.dataSource.gallery

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.dataSource.gallery.model.PhotoInfoDto

internal interface GalleryDataSource {
    suspend fun scanGallery(): Flow<List<PhotoInfoDto>>
    suspend fun fetchLastPhoto(): PhotoInfoDto?
}