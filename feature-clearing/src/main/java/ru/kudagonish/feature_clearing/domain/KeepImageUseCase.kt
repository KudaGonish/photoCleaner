package ru.kudagonish.feature_clearing.domain

import ru.kudagonish.photofinder.domain.ImagesRepository

internal class KeepImageUseCase(
    private val repository: ImagesRepository
) {
    suspend operator fun invoke(uri: String) = repository.keepPhoto(uri)
}