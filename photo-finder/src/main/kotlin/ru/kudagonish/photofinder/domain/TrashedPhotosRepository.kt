package ru.kudagonish.photofinder.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.kudagonish.photofinder.domain.models.ImageModel

interface TrashedPhotosRepository {
    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotoCount(): Flow<Int>

    suspend fun getPhotoUris(): List<String>
    suspend fun markPhotoAsTrashed(
        uris: List<String>,
        date: LocalDate
    )
    suspend fun restorePhoto(uri: String)
}