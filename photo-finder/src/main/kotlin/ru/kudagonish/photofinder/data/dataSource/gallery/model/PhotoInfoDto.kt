package ru.kudagonish.photofinder.data.dataSource.gallery.model

import kotlinx.datetime.LocalDate

internal data class PhotoInfoDto(
    val uri: String,
    val dateAdded: LocalDate,
)