package ru.kudagonish.photofinder.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.data.db.Tables
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.data.db.entity.UriColumn

@Dao
interface GalleryInformationDao {
    @Query("SELECT * FROM ${Tables.GalleryInformation.NAME} WHERE takenTimestamp = :date")
    fun getPhotos(date: Long): Flow<List<GalleryInformationEntity>>

    @Query("SELECT * FROM ${Tables.GalleryInformation.NAME}")
    fun getPhotos(): Flow<List<GalleryInformationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<GalleryInformationEntity>)

    @Delete(GalleryInformationEntity::class)
    suspend fun deletePhotoByUri(uri: UriColumn)
}