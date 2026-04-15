package ru.kudagonish.photofinder.data.repository

import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.kudagonish.photofinder.data.dataSource.gallery.GalleryDataSource
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.domain.GalleryRepository

internal class GalleryRepositoryImpl(
    private val galleryDataSource: GalleryDataSource,
    private val galleryInformationDao: GalleryInformationDao
) : GalleryRepository {

    override suspend fun scanGallery() {
        val timeZone = TimeZone.currentSystemDefault()
        galleryDataSource.scanGallery().collect { photos ->
            val entities = photos.map { dto ->
                val timestamp = dto.dateAdded.atStartOfDayIn(timeZone).toEpochMilliseconds()

                GalleryInformationEntity(
                    uri = dto.uri,
                    takenTimestamp = timestamp,
                    plannedDeletionTimestamp = null
                )
            }
            galleryInformationDao.insertPhotos(entities)
        }
    }

    override suspend fun addLastPhoto() {
        galleryDataSource.fetchLastPhoto()?.let { dto ->
            val timeZone = TimeZone.currentSystemDefault()
            val timestamp = dto.dateAdded.atStartOfDayIn(timeZone).toEpochMilliseconds()

            val entity = GalleryInformationEntity(
                uri = dto.uri,
                takenTimestamp = timestamp,
                plannedDeletionTimestamp = null
            )
            galleryInformationDao.insertPhotos(listOf(entity))
        }
    }
}
