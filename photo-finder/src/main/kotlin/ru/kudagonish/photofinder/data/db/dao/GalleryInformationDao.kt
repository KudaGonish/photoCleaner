package ru.kudagonish.photofinder.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.db.Tables
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.data.db.entity.PhotoStatus
import ru.kudagonish.photofinder.data.db.entity.UriColumn

@Dao
interface GalleryInformationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhotos(photos: List<GalleryInformationEntity>)

    //region Flow по типам списков: Для распределения, корзина, на удаление
    @Query(
        "SELECT * " +
                "FROM ${Tables.GalleryInformation.NAME} " +
                "WHERE status = :status"
    )
    fun getPhotos(status: PhotoStatus): Flow<List<GalleryInformationEntity>>

    @Query(
        "SELECT * " +
                "FROM ${Tables.GalleryInformation.NAME} " +
                "WHERE takenTimestamp = :date AND status = :status"
    )
    fun getPhotos(date: Long, status: PhotoStatus): Flow<List<GalleryInformationEntity>>
    //endregion

    @Query(
        "UPDATE ${Tables.GalleryInformation.NAME} " +
                "SET plannedDeletionTimestamp = :timestamp, status = :status " +
                "WHERE uri = :uri"
    )
    suspend fun markAsTrashed(
        uri: String,
        timestamp: Long,
        status: PhotoStatus = PhotoStatus.NEED_PUT_TO_TRASH
    )

    @Query(
        "UPDATE ${Tables.GalleryInformation.NAME} " +
                "SET status = :status " +
                "WHERE uri = :uri"
    )
    suspend fun markAsDeletion(
        uri: String,
        status: PhotoStatus = PhotoStatus.DELETION
    )

    @Query(
        "UPDATE ${Tables.GalleryInformation.NAME} " +
                "SET status = :status, plannedDeletionTimestamp = null " +
                "WHERE uri = :uri"
    )
    suspend fun markAsActive(
        uri: String,
        status: PhotoStatus = PhotoStatus.ACTIVE
    )

    @Delete(GalleryInformationEntity::class)
    suspend fun removePhoto(uri: UriColumn)

    @Delete(GalleryInformationEntity::class)
    suspend fun removePhotos(uris: List<UriColumn>)
}