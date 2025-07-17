package com.moly.edi.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.moly.edi.domain.repository.ProjectRepository
import javax.inject.Inject

class ProjectSyncWorker @Inject constructor(
    context: Context,
    workerParams: WorkerParameters,
    private val projectRepository: ProjectRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val userId = inputData.getString("userId") ?: return Result.failure()
        val repository = projectRepository

        return try {
            repository.syncProjects(userId)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

fun scheduleProjectSync(context: Context, userId: String) {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val data = Data.Builder()
        .putString("userId", userId)
        .build()

    val syncRequest = OneTimeWorkRequestBuilder<ProjectSyncWorker>()
        .setConstraints(constraints)
        .setInputData(data)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniqueWork(
            "project_sync",
            ExistingWorkPolicy.KEEP,
            syncRequest
        )
}