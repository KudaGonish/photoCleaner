package ru.kudagonish.photofinder.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kudagonish.photofinder.data.db.Tables

@Entity(tableName = Tables.GalleryInformation.NAME)
data class GalleryInformationEntity(
    @PrimaryKey val uri: String,
    val takenTimestamp: Long,
    val plannedDeletionTimestamp: Long?
)

data class UriColumn(
    val uri: String
)