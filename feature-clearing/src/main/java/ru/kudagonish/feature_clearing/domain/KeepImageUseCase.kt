package ru.kudagonish.feature_clearing.domain

import ru.kudagonish.photofinder.domain.PhotosRepository

internal class KeepImageUseCase(
    private val repository: PhotosRepository
) {
    suspend operator fun invoke(uri: String) = repository.keepPhoto(uri)
}