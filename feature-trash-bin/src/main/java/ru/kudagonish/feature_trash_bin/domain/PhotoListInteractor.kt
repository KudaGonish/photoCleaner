package ru.kudagonish.feature_trash_bin.domain

import ru.kudagonish.photofinder.domain.DeletionPhotosRepository
import ru.kudagonish.photofinder.domain.TrashedPhotosRepository

internal class PhotoListInteractor(
    private val trashedPhotosRepository: TrashedPhotosRepository,
    private val deletionPhotosRepository: DeletionPhotosRepository
) {
    fun getTrashedPhoto() = trashedPhotosRepository.getPhotos()
    fun getDeletionPhoto() = deletionPhotosRepository.getPhotos()
}