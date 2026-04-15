package ru.kudagonish.photofinder.domain

import android.app.PendingIntent

interface PhotoRequestRepository {
    fun createDeleteRequest(uris: List<String>): PendingIntent
    fun createTrashRequest(uris: List<String>, isPut: Boolean): PendingIntent
}