package ru.kudagonish.photofinder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.domain.DeletionPhotosRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class DeletionPhotoRepositoryImpl(
    private val dataSource: ImagesLocalDataSource
) : DeletionPhotosRepository {
    override fun getPhotos(): Flow<List<ImageModel>> {
        return dataSource.getDeletionPhotos().map { entities ->
            entities.map { entity -> ImageModel(src = entity.uri) }
        }
    }

    override fun getPhotoCount(): Flow<Int> = dataSource.getDeletionPhotoCount()

    override suspend fun restorePhoto(uri: String) {
        dataSource.restorePhoto(uri)
    }
}