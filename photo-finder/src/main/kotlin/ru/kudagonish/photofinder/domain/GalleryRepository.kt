package ru.kudagonish.photofinder.domain

interface GalleryRepository {
    suspend fun scanGallery()
    suspend fun addLastPhoto()
}