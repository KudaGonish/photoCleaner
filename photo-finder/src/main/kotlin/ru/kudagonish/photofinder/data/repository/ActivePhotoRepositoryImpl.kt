package ru.kudagonish.photofinder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.domain.ActivePhotosRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class ActivePhotoRepositoryImpl(
    private val dataSource: ImagesLocalDataSource,
) : ActivePhotosRepository {
    private val timeZone = TimeZone.currentSystemDefault()

    override fun getPhotos(): Flow<List<ImageModel>> {
        return dataSource.getActivePhotos().map { entities ->
            entities.map { entity -> ImageModel(src = entity.uri) }
        }
    }

    override fun getPhotos(date: LocalDate): Flow<List<ImageModel>> {
        val timestamp = date.atStartOfDayIn(timeZone).toEpochMilliseconds()
        return dataSource.getActivePhotos(timestamp).map { entities ->
            entities.map { entity -> ImageModel(src = entity.uri) }
        }
    }

    override suspend fun keepPhoto(uri: String) =
        dataSource.removePhotoFromStorage(uri)

    override suspend fun markPhotoAsNeedToTrash(uri: String) =
        dataSource.markPhotoAsNeedToTrash(uri)

    override suspend fun markPhotoAsDeletion(uri: String) =
        dataSource.markPhotoAsDeletion(uri)
}