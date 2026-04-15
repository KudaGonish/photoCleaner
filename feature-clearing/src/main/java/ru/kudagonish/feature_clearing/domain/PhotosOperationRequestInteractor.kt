package ru.kudagonish.feature_clearing.domain

import android.app.PendingIntent
import kotlinx.datetime.LocalDate
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.photofinder.domain.DeletionPhotosRepository
import ru.kudagonish.photofinder.domain.PhotoRequestRepository
import ru.kudagonish.photofinder.domain.TrashedPhotosRepository

internal class PhotosOperationRequestInteractor(
    private val photosRepository: PhotoRequestRepository,
    private val trashedPhotosRepository: TrashedPhotosRepository,
    private val deletionPhotosRepository: DeletionPhotosRepository
) {
    suspend operator fun invoke(deletionType: DeletionType): PendingIntent {
        return when (deletionType) {
            is DeletionType.SystemTrash -> {
                val uris = trashedPhotosRepository.getPhotoUris()
                photosRepository.createTrashRequest(uris, true)
            }

            is DeletionType.Instant -> {
                val uris = deletionPhotosRepository.getPhotoUris()
                photosRepository.createDeleteRequest(uris)
            }
        }
    }

    suspend fun completeNegativeAction(deletionType: DeletionType, date: LocalDate) {
        when (deletionType) {
            DeletionType.Instant -> {
                val uris = deletionPhotosRepository.getPhotoUris()
                deletionPhotosRepository.clearPhotos(uris)
            }

            DeletionType.SystemTrash -> {
                val uris = trashedPhotosRepository.getPhotoUris()
                trashedPhotosRepository.markPhotoAsTrashed(uris, date)
            }
        }
    }
}