package ru.kudagonish.photofinder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.domain.ImagesRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class ImagesRepositoryImpl(
    private val imagesLocalDataSource: ImagesLocalDataSource
) : ImagesRepository {

    override fun getPhotos(): Flow<List<ImageModel>> {
        return imagesLocalDataSource.getPhotos().map { entities ->
            entities.map { entity ->
                ImageModel(src = entity.uri)
            }
        }
    }

    override fun getPhotos(timeStamp: Long): Flow<List<ImageModel>> {
        return imagesLocalDataSource.getPhotos(timeStamp).map { entities ->
            entities.map { entity ->
                ImageModel(src = entity.uri)
            }
        }
    }
}