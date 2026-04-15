package ru.kudagonish.photofinder.data.dataSource.gallery

import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.net.toUri
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.kudagonish.core.ContextProvider
import ru.kudagonish.photofinder.data.dataSource.gallery.model.PhotoInfoDto
import kotlin.time.Instant

private const val PAGE_SIZE = 500

internal class GalleryDataSourceImpl(private val contextProvider: ContextProvider) : GalleryDataSource {

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

    override fun createDeleteRequest(uris: List<String>): PendingIntent {
        val contentUris = uris.map { it.toUri() }
        return MediaStore.createDeleteRequest(contextProvider.context.contentResolver, contentUris)
    }

    override fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent {
        val contentUris = uris.map { it.toUri() }
        return MediaStore.createTrashRequest(contextProvider.context.contentResolver, contentUris, isPut)
    }

    private fun queryPhotos(queryArgs: Bundle): List<PhotoInfoDto> {
        val photos = mutableListOf<PhotoInfoDto>()

        contextProvider.context.contentResolver.query(
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

        val date = Instant.fromEpochSeconds(dateAdded)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date

        return PhotoInfoDto(
            uri = contentUri.toString(),
            dateAdded = date,
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

    private fun getSelectionParams() =
        "${MediaStore.MediaColumns.IS_TRASHED} = 0 AND ${MediaStore.MediaColumns.IS_PENDING} = 0"
}
