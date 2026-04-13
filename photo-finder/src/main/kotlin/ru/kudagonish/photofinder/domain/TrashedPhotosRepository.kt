package ru.kudagonish.photofinder.domain

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.domain.models.ImageModel

interface TrashedPhotosRepository {
    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotoCount(): Flow<Int>

    suspend fun restorePhoto(uri: String)
}