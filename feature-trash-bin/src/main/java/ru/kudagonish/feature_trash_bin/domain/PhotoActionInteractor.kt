package ru.kudagonish.feature_trash_bin.domain

import android.app.PendingIntent
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.photofinder.domain.DeletionPhotosRepository
import ru.kudagonish.photofinder.domain.PhotoRequestRepository
import ru.kudagonish.photofinder.domain.TrashedPhotosRepository

internal class PhotoActionInteractor(
    private val photoRequestRepository: PhotoRequestRepository,
    private val deletionPhotoRepository: DeletionPhotosRepository,
    private val trashPhotoRepository: TrashedPhotosRepository
) {
    suspend fun deleteAllPhotos(type: DeletionType): PendingIntent {
        val photos = when (type) {
            DeletionType.Instant -> deletionPhotoRepository.getPhotoUris()
            DeletionType.SystemTrash -> trashPhotoRepository.getTrashedPhotoUris()
        }
        return photoRequestRepository.createDeleteRequest(photos)
    }

    suspend fun completePhotosDeletion(type: DeletionType) {
        val photos = when (type) {
            DeletionType.Instant -> deletionPhotoRepository.getPhotoUris()
            DeletionType.SystemTrash -> trashPhotoRepository.getTrashedPhotoUris()
        }
        deletionPhotoRepository.clearPhotos(photos)
    }

    fun deletePhoto(uri: String): PendingIntent {
        return photoRequestRepository.createDeleteRequest(listOf(uri))
    }

    suspend fun completePhotoDeletion(uri: String) {
        deletionPhotoRepository.clearPhotos(listOf(uri))
    }

    suspend fun restorePhoto(uri: String,type: DeletionType){
        when (type) {
            DeletionType.Instant -> deletionPhotoRepository.restorePhoto(uri)
            DeletionType.SystemTrash -> trashPhotoRepository.restorePhoto(uri)
        }
    }
}