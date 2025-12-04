package com.example.assignmenttrack.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.assignmenttrack.database.TodoDatabase

class LateTaskWorker(
//    comment idk
    context: Context,
    params: WorkerParameters
): CoroutineWorker(appContext = context, params = params){
    override suspend fun doWork(): Result {
        val taskId = inputData.getInt("TASK_ID", -1)

        if (taskId == -1){
            return Result.failure()
        }

        return try {
            val database = TodoDatabase.getDatabase(applicationContext)
            database.getTaskDao().lateTask(taskId = taskId)
            Result.success()
        } catch (e: Exception){
            Result.retry()
        }
    }
}