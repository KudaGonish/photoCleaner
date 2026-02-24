package ru.kudagonish.photofinder

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import java.util.Date

data class PhotoInfo(
    val uri: String,
    val dateAdded: Date
)

class GalleryScanner(private val context: Context) {

    fun getImages(): List<PhotoInfo> {
        val images = mutableListOf<PhotoInfo>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val dateAdded = cursor.getLong(dateAddedColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                images.add(PhotoInfo(contentUri.toString(), Date(dateAdded * 1000)))
            }
        }
        return images
    }
}
