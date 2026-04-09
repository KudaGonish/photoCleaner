package ru.kudagonish.photofinder.domain

import kotlinx.coroutines.flow.Flow
import ru.kudagonish.photofinder.domain.models.ImageModel

interface ImagesRepository {

    fun getPhotos(): Flow<List<ImageModel>>
    fun getPhotos(timeStamp: Long): Flow<List<ImageModel>>

}