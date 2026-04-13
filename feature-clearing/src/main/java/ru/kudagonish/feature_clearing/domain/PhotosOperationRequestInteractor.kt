package ru.kudagonish.feature_clearing.domain

import android.app.PendingIntent
import kotlinx.coroutines.flow.first
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.photofinder.domain.PhotoRequestRepository

internal class PhotosOperationRequestInteractor(
    private val photosRepository: PhotoRequestRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(uris: List<String>): PendingIntent {
        val settings = settingsRepository.settingsFlow.first()

        return when (settings.deletionType) {
            is DeletionType.SystemTrash -> photosRepository.createTrashRequest(uris, true)
            is DeletionType.Instant -> photosRepository.createDeleteRequest(uris)
        }
    }
}