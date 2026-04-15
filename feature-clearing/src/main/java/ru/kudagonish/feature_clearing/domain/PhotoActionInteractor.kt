package ru.kudagonish.feature_clearing.domain

import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.photofinder.domain.ActivePhotosRepository

internal class PhotoActionInteractor(
    private val repository: ActivePhotosRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend fun positive(uri: String) = repository.keepPhoto(uri)

    suspend fun negative(uri: String, date: LocalDate) {
        val settings = settingsRepository.settingsFlow.first()

        when (settings.deletionType) {
            DeletionType.Instant -> repository.markPhotoAsDeletion(uri)
            DeletionType.SystemTrash -> repository.markPhotoAsTrashed(uri, date)
        }
    }
}