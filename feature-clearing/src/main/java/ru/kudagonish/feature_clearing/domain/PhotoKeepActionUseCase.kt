package ru.kudagonish.feature_clearing.domain

import ru.kudagonish.photofinder.domain.ActivePhotosRepository

internal class PhotoKeepActionUseCase(
    private val repository: ActivePhotosRepository
) {
    suspend operator fun invoke(uri: String) = repository.keepPhoto(uri)
}