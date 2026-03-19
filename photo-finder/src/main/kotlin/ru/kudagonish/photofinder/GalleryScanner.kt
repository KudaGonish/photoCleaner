package ru.kudagonish.photofinder

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

private const val SECONDS_TO_MILLIS = 1000L
private const val PAGE_SIZE = 500

data class PhotoInfo(
    val uri: String,
    val dateAdded: Date
)

class GalleryScanner(private val context: Context) {

    fun scanGallery(): Flow<List<PhotoInfo>> = flow {
        var offset = 0
        var hasMore = true

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )

        while (hasMore) {
            val imagesChunk = mutableListOf<PhotoInfo>()

            val queryArgs = getQueryParams(offset)

            val cursor = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                queryArgs,
                null
            )

            cursor?.use { c ->
                val idColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val dateAddedColumn = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

                while (c.moveToNext()) {
                    val id = c.getLong(idColumn)
                    val dateAdded = c.getLong(dateAddedColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    imagesChunk.add(
                        PhotoInfo(
                            uri = contentUri.toString(),
                            dateAdded = Date(dateAdded * SECONDS_TO_MILLIS)
                        )
                    )
                }
            }

            if (imagesChunk.isNotEmpty()) {
                emit(imagesChunk)

                if (imagesChunk.size < PAGE_SIZE) {
                    hasMore = false // Данные закончились
                } else {
                    offset += PAGE_SIZE
                }
            } else {
                hasMore = false
            }
        }
    }

    private fun getQueryParams(offset: Int) = Bundle().apply {
        putString(ContentResolver.QUERY_ARG_SQL_SELECTION, getSelectionParams())
        putStringArray(
            ContentResolver.QUERY_ARG_SORT_COLUMNS,
            arrayOf(MediaStore.Images.Media.DATE_ADDED)
        )
        putInt(
            ContentResolver.QUERY_ARG_SORT_DIRECTION,
            ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
        )
        putInt(ContentResolver.QUERY_ARG_LIMIT, PAGE_SIZE)
        putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
    }


    private fun getSelectionParams() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
            "${MediaStore.MediaColumns.IS_TRASHED} = 0 AND ${MediaStore.MediaColumns.IS_PENDING} = 0"

        else -> "${MediaStore.MediaColumns.IS_PENDING} = 0"
    }
}