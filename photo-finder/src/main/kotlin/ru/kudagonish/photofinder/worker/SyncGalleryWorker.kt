package ru.kudagonish.photofinder.worker

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import ru.kudagonish.core.checkPermissionGranted
import ru.kudagonish.photofinder.domain.GalleryRepository
import java.util.concurrent.TimeUnit

class SyncGalleryWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: GalleryRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (!applicationContext.checkPermissionGranted())
            return Result.success()

        return try {
            repository.addLastPhoto()
            Log.d("SyncGalleryWorker", "Last photo added to database")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncGalleryWorker", "Error adding last photo", e)
            Result.retry()
        } finally {
            withContext(NonCancellable) {
                schedulePeriodicWorkRequest(applicationContext)
            }
        }
    }

    companion object {
        private const val WORKER_NAME = "SyncGalleryWorker"

        fun schedulePeriodicWorkRequest(context: Context) {
            val constraints = Constraints.Builder()
                .addContentUriTrigger(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true)
                .build()

            val request = OneTimeWorkRequestBuilder<SyncGalleryWorker>()
                .setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                request
            )
        }
    }
}
