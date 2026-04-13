package ru.kudagonish.photofinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kudagonish.photofinder.data.db.dao.GalleryInformationDao
import ru.kudagonish.photofinder.data.db.entity.GalleryInformationEntity
import ru.kudagonish.photofinder.data.db.entity.PhotoStatusConverter

@Database(
    entities = [GalleryInformationEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(PhotoStatusConverter::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun galleryInformationDao(): GalleryInformationDao
}
