package ru.kudagonish.feature_clearing.domain

import kotlinx.coroutines.flow.first
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.photofinder.domain.ActivePhotosRepository

internal class PhotoActionInteractor(
    private val activeRepository: ActivePhotosRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend fun positive(uri: String) = activeRepository.keepPhoto(uri)

    suspend fun negative(uri: String) {
        val settings = settingsRepository.settingsFlow.first()

        when (settings.deletionType) {
            DeletionType.Instant -> activeRepository.markPhotoAsDeletion(uri)
            DeletionType.SystemTrash -> activeRepository.markPhotoAsNeedToTrash(uri)
        }
    }
}