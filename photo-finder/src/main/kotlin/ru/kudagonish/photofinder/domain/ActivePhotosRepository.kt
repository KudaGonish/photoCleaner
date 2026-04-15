package ru.kudagonish.photofinder.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.kudagonish.photofinder.domain.models.ImageModel

interface ActivePhotosRepository {
    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotos(date: LocalDate): Flow<List<ImageModel>>

    suspend fun keepPhoto(uri: String)
    suspend fun markPhotoAsNeedToTrash(uri: String)
    suspend fun markPhotoAsDeletion(uri: String)
}