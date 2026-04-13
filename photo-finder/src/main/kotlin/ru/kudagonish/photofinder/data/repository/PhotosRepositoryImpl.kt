package ru.kudagonish.photofinder.data.repository

import android.app.PendingIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.domain.PhotosRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class PhotosRepositoryImpl(
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val galleryDataSource: GalleryDataSource
) : PhotosRepository {

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

    override suspend fun keepPhoto(uri: String) {
        imagesLocalDataSource.removePhotoFromStorage(uri)
    }

    override suspend fun deletePhoto(uris: List<String>) {
        imagesLocalDataSource.deletePhoto(uris)
    }

    override suspend fun markAsTrashed(uris: List<String>, date: LocalDate) {
        val timestamp = date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        imagesLocalDataSource.markPhotoAsTrashed(uris, timestamp)
    }

    override fun createDeleteRequest(uris: List<String>): PendingIntent {
        return galleryDataSource.createDeleteRequest(uris)
    }

    override fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent {
        return galleryDataSource.createTrashRequest(uris, isPut)
    }
}