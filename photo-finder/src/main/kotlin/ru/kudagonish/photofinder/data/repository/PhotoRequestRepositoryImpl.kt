package ru.kudagonish.photofinder.data.repository

import android.app.PendingIntent
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.domain.PhotoRequestRepository

internal class PhotoRequestRepositoryImpl(
    private val galleryDataSource: GalleryDataSource
) : PhotoRequestRepository {

    override fun createDeleteRequest(uris: List<String>): PendingIntent {
        return galleryDataSource.createDeleteRequest(uris)
    }

    override fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent {
        return galleryDataSource.createTrashRequest(uris, isPut)
    }
}