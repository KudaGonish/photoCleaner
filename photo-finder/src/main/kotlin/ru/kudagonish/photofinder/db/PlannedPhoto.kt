package ru.kudagonish.photofinder.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planned_photos")
data class PlannedPhoto(
    @PrimaryKey val uri: String,
    val takenDate: Long,
    val plannedDate: String // Формат: "dd-MM"
)
