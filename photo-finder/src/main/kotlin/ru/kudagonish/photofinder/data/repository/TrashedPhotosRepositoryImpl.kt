package ru.kudagonish.photofinder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.domain.TrashedPhotosRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class TrashedPhotosRepositoryImpl(
    private val dataSource: ImagesLocalDataSource
) : TrashedPhotosRepository {
    override fun getPhotos(): Flow<List<ImageModel>> {
        return dataSource.getTrashedPhotos().map { entities ->
            entities.map { entity -> ImageModel(src = entity.uri) }
        }
    }

    override fun getPhotoCount(): Flow<Int> = dataSource.getTrashPhotoCount()

    override suspend fun restorePhoto(uri: String) {
        dataSource.restorePhoto(uri)
    }
}