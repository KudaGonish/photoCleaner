package ru.kudagonish.photofinder.domain.useCase

import ru.kudagonish.photofinder.domain.GalleryRepository

internal class ScanGalleryUseCase(private val repository: GalleryRepository):IScanGalleryUseCase {
    override suspend fun invoke() {
        repository.scanGallery()
    }
}

interface IScanGalleryUseCase {
    suspend operator fun invoke()
}