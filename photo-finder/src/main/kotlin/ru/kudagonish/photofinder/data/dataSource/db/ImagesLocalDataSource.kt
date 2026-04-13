package ru.kudagonish.photofinder.data.dataSource.db

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

internal interface ImagesLocalDataSource {
    // Фото ожидающие распределение
    fun getActivePhotos(): Flow<List<GalleryInformationEntity>>
    fun getActivePhotos(date: Long): Flow<List<GalleryInformationEntity>>

    // Фото помеченные корзиной
    fun getTrashedPhotos(): Flow<List<GalleryInformationEntity>>
    fun getTrashPhotoCount(): Flow<Int>

    // Фото помеченные на удаление
    fun getDeletionPhotos(): Flow<List<GalleryInformationEntity>>
    fun getDeletionPhotoCount(): Flow<Int>

    // Методы по работе с ожидающими фото
    suspend fun markPhotoAsTrashed(uri: String, timestamp: Long)
    suspend fun markPhotoAsDeletion(uri: String)

    // Общие методы
    suspend fun removePhotoFromStorage(uri: String)
    suspend fun removePhotoFromStorage(uris: List<String>)
    suspend fun restorePhoto(uri: String)
}