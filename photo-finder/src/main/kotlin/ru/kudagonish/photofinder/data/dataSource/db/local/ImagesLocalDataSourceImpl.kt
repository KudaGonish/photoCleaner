package ru.kudagonish.photofinder.data.dataSource.db.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kudagonish.photofinder.data.dataSource.db.ImagesLocalDataSource
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.data.db.entity.PhotoStatus
import ru.kudagonish.photofinder.data.db.entity.UriColumn

internal class ImagesLocalDataSourceImpl(
    private val galleryInformationDao: GalleryInformationDao
) : ImagesLocalDataSource {

    override fun getActivePhotos(): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos(status = PhotoStatus.ACTIVE)

    override fun getActivePhotos(date: Long): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos(date = date, status = PhotoStatus.ACTIVE)

    override fun getTrashedPhotos(): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos(status = PhotoStatus.TRASH)

    override fun getTrashPhotoCount(): Flow<Int> =
        galleryInformationDao.getPhotos(status = PhotoStatus.NEED_PUT_TO_TRASH).map { it.count() }

    override suspend fun getTrashedPhotoUris(): List<String> =
        galleryInformationDao.getPhotoUris(status = PhotoStatus.NEED_PUT_TO_TRASH)

    override fun getDeletionPhotos(): Flow<List<GalleryInformationEntity>> =
        galleryInformationDao.getPhotos(status = PhotoStatus.DELETION)

    override fun getDeletionPhotoCount(): Flow<Int> =
        galleryInformationDao.getPhotos(status = PhotoStatus.DELETION).map { it.count() }

    override suspend fun getDeletionPhotoUris(): List<String> =
        galleryInformationDao.getPhotoUris(status = PhotoStatus.DELETION)

    override suspend fun markPhotoAsNeedToTrash(uri: String) =
        galleryInformationDao.markAsNeedToTrash(uri)

    override suspend fun markPhotosAsTrashed(uris: List<String>, timestamp: Long) =
        galleryInformationDao.markAsTrashed(uris,timestamp)

    override suspend fun markPhotoAsDeletion(uri: String) =
        galleryInformationDao.markAsDeletion(uri)

    override suspend fun removePhotoFromStorage(uri: String) =
        galleryInformationDao.removePhoto(UriColumn(uri))

    override suspend fun removePhotoFromStorage(uris: List<String>) =
        galleryInformationDao.removePhotos(uris.map { UriColumn(it) })

    override suspend fun restorePhoto(uri: String) =
        galleryInformationDao.markAsActive(uri)
}