package ru.kudagonish.photofinder.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import ru.kudagonish.photofinder.data.db.Tables

/**
 * @param plannedDeletionTimestamp Используется в связке [PhotoStatus.TRASH]
 */
@Entity(tableName = Tables.GalleryInformation.NAME)
data class GalleryInformationEntity(
    @PrimaryKey val uri: String,
    val takenTimestamp: Long,
    val status: Int = PhotoStatus.ACTIVE.id,
    val plannedDeletionTimestamp: Long? = null
)

enum class PhotoStatus(val id: Int) {
    ACTIVE(0),
    DELETION(-1),
    TRASH(1),
    NEED_PUT_TO_TRASH(2),
}

internal class PhotoStatusConverter {
    @TypeConverter
    fun fromStatus(status: PhotoStatus): Int = status.id

    @TypeConverter
    fun toStatus(value: Int): PhotoStatus = PhotoStatus.entries.first { it.id == value }
}

data class UriColumn(
    val uri: String
)