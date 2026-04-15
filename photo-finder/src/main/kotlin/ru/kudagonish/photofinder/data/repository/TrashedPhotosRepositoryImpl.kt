package ru.kudagonish.photofinder.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.domain.TrashedPhotosRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class TrashedPhotosRepositoryImpl(
    private val dataSource: ImagesLocalDataSource
) : TrashedPhotosRepository {
    private val timeZone = TimeZone.currentSystemDefault()

    override fun getPhotos(): Flow<List<ImageModel>> {
        return dataSource.getTrashedPhotos().map { entities ->
            entities.map { entity -> ImageModel(src = entity.uri) }
        }
    }

    override fun getPhotoCount(): Flow<Int> = dataSource.getTrashPhotoCount()

    override suspend fun getPhotoUris(): List<String> =
        dataSource.getTrashedPhotoUris()

    override suspend fun markPhotoAsTrashed(
        uris: List<String>,
        date: LocalDate
    ) = date.atStartOfDayIn(timeZone).toEpochMilliseconds().let { timestamp ->
        dataSource.markPhotosAsTrashed(uris, timestamp)
    }

    override suspend fun restorePhoto(uri: String) {
        dataSource.restorePhoto(uri)
    }
}