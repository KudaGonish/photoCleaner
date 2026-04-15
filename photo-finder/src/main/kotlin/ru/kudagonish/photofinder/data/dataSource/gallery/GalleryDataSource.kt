package ru.kudagonish.photofinder.data.dataSource.gallery

import android.app.PendingIntent
import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.dataSource.gallery.model.PhotoInfoDto

internal interface GalleryDataSource {
    suspend fun scanGallery(): Flow<List<PhotoInfoDto>>
    suspend fun fetchLastPhoto(): PhotoInfoDto?
    fun createDeleteRequest(uris: List<String>): PendingIntent
    fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent
}