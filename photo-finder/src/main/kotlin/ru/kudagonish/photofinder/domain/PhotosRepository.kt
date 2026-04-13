package ru.kudagonish.photofinder.domain

import android.app.PendingIntent
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.kudagonish.photofinder.domain.models.ImageModel

interface PhotosRepository {

    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotos(timeStamp: Long): Flow<List<ImageModel>>

    suspend fun keepPhoto(uri: String)
    suspend fun deletePhoto(uris: List<String>)
    suspend fun markAsTrashed(uris: List<String>, date: LocalDate)
    fun createDeleteRequest(uris: List<String>): PendingIntent
    fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent
}