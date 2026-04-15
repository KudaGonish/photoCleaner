package ru.kudagonish.photofinder.domain

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.domain.models.ImageModel

interface DeletionPhotosRepository {
    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotoCount(): Flow<Int>

    suspend fun getPhotoUris(): List<String>
    suspend fun clearPhotos(uris: List<String>)
    suspend fun restorePhoto(uri: String)
}