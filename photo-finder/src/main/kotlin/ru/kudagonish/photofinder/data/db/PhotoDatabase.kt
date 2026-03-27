package ru.kudagonish.photofinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity

@Database(
    entities = [GalleryInformationEntity::class],
    version = 1,
    exportSchema = true
)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun galleryInformationDao(): GalleryInformationDao
}
