package ru.kudagonish.photofinder.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlannedPhotoDao {
    @Query("SELECT * FROM planned_photos WHERE plannedDate = :date")
    fun getPhotosForDate(date: String): Flow<List<PlannedPhoto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PlannedPhoto>)

    @Query("DELETE FROM planned_photos WHERE plannedDate != :currentDate AND plannedDate != :tomorrowDate")
    suspend fun clearOldPhotos(currentDate: String, tomorrowDate: String)
    
    @Query("DELETE FROM planned_photos WHERE uri = :uri")
    suspend fun deleteByUri(uri: String)
}
