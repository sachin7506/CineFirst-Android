package com.platformcommons.cinefirst.workers


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.platformcommons.cinefirst.model.AddUserRequest
import com.platformcommons.cinefirst.model.AppDatabase

import com.platformcommons.cinefirst.remote.RetrofitClient
import retrofit2.HttpException

class UserSyncWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val dao = AppDatabase.getDatabase(context).pendingUserDao()
        val pendingUsers = dao.getAll()


        for (user in pendingUsers) {
            try {
                val request = AddUserRequest(user.name, user.job)
                val response = RetrofitClient.api.addUser(request)
                if (response.isSuccessful) {
                    dao.delete(user)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                return Result.retry()
            } catch (e: Exception) {
                e.printStackTrace()
                return Result.retry()
            }
        }

        return Result.success()
    }
}
fun enqueueUserSyncWork(context: Context) {
    val request = OneTimeWorkRequestBuilder<UserSyncWorker>().build()
    WorkManager.getInstance(context).enqueueUniqueWork(
        "user_sync_work",
        ExistingWorkPolicy.KEEP,
        request
    )
}
