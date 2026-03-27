package ru.kudagonish.photofinder.data.dataSource.gallery

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.kudagonish.photofinder.data.dataSource.gallery.model.PhotoInfoDto
import java.util.Date

private const val SECONDS_TO_MILLIS = 1000L
private const val PAGE_SIZE = 500

internal class GalleryDataSourceImpl(private val context: Context) : GalleryDataSource {

    private val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATE_ADDED,
    )

    override suspend fun scanGallery(): Flow<List<PhotoInfoDto>> = flow {
        var offset = 0
        var hasMore = true

        while (hasMore) {
            val queryArgs = buildQueryArgs(limit = PAGE_SIZE, offset = offset)
            val imagesChunk = queryPhotos(queryArgs)

            if (imagesChunk.isNotEmpty()) {
                emit(imagesChunk)
                if (imagesChunk.size < PAGE_SIZE) {
                    hasMore = false
                } else {
                    offset += PAGE_SIZE
                }
            } else {
                hasMore = false
            }
        }
    }

    override suspend fun fetchLastPhoto(): PhotoInfoDto? {
        val queryArgs = buildQueryArgs(limit = 1)
        return queryPhotos(queryArgs).firstOrNull()
    }

    private fun queryPhotos(queryArgs: Bundle): List<PhotoInfoDto> {
        val photos = mutableListOf<PhotoInfoDto>()

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            queryArgs,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                photos.add(cursor.toPhotoInfoDto())
            }
        }
        return photos
    }

    private fun Cursor.toPhotoInfoDto(): PhotoInfoDto {
        val idColumn = getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val dateAddedColumn = getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

        val id = getLong(idColumn)
        val dateAdded = getLong(dateAddedColumn)

        val contentUri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id
        )

        return PhotoInfoDto(
            uri = contentUri.toString(),
            dateAdded = Date(dateAdded * SECONDS_TO_MILLIS),
        )
    }

    private fun buildQueryArgs(limit: Int, offset: Int = 0) = Bundle().apply {
        putString(ContentResolver.QUERY_ARG_SQL_SELECTION, getSelectionParams())
        putStringArray(
            ContentResolver.QUERY_ARG_SORT_COLUMNS,
            arrayOf(MediaStore.Images.Media.DATE_ADDED)
        )
        putInt(
            ContentResolver.QUERY_ARG_SORT_DIRECTION,
            ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
        )
        putInt(ContentResolver.QUERY_ARG_LIMIT, limit)
        putInt(ContentResolver.QUERY_ARG_OFFSET, offset)
    }

    private fun getSelectionParams() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
            "${MediaStore.MediaColumns.IS_TRASHED} = 0 AND ${MediaStore.MediaColumns.IS_PENDING} = 0"

        else -> "${MediaStore.MediaColumns.IS_PENDING} = 0"
    }
}
