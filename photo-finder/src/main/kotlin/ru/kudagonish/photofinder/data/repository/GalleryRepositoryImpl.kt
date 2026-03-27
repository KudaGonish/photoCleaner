package ru.kudagonish.photofinder.data.repository

import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.domain.GalleryRepository

internal class GalleryRepositoryImpl(
    private val galleryDataSource: GalleryDataSource,
    private val galleryInformationDao: GalleryInformationDao
) : GalleryRepository {

    override suspend fun scanGallery() {
        galleryDataSource.scanGallery().collect { photos ->
            val entities = photos.map { dto ->
                GalleryInformationEntity(
                    uri = dto.uri,
                    takenTimestamp = dto.dateAdded.time,
                    plannedDeletionTimestamp = null
                )
            }
            galleryInformationDao.insertPhotos(entities)
        }
    }

    override suspend fun addLastPhoto() {
        galleryDataSource.fetchLastPhoto()?.let { dto ->
            val entity = GalleryInformationEntity(
                uri = dto.uri,
                takenTimestamp = dto.dateAdded.time,
                plannedDeletionTimestamp = null
            )
            galleryInformationDao.insertPhotos(listOf(entity))
        }
    }
}
