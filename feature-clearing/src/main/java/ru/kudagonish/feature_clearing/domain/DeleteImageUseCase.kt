package ru.kudagonish.feature_clearing.domain

import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import ru.kudagonish.datastore.settings.models.DeletionType
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.photofinder.domain.PhotosRepository

internal class DeleteImageUseCase(
    private val repository: PhotosRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(uris: List<String>, date: LocalDate) {
        val settings = settingsRepository.settingsFlow.first()

        when (settings.deletionType) {
            DeletionType.Instant -> repository.deletePhoto(uris)
            DeletionType.SystemTrash -> repository.markAsTrashed(uris, date)
        }
    }
}