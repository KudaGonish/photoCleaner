package ru.kudagonish.feature_clearing.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import ru.kudagonish.datastore.settings.models.WorkAlgorithm
import ru.kudagonish.feature_settings.domain.SettingsRepository
import ru.kudagonish.photofinder.domain.ImagesRepository
import ru.kudagonish.photofinder.domain.models.ImageModel

internal class GetImagesUseCase(
    private val settingsRepository: SettingsRepository,
    private val imagesRepository: ImagesRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(date: LocalDate): Flow<List<ImageModel>> {
        val timestamp = date.toEpochDays()

        return settingsRepository.settingsFlow
            .map { it.algorithm }
            .distinctUntilChanged()
            .flatMapLatest { algorithm ->
                if (algorithm is WorkAlgorithm.DayMoth) {
                    imagesRepository.getPhotos(timestamp)
                } else {
                    imagesRepository.getPhotos()
                }
            }
    }
}