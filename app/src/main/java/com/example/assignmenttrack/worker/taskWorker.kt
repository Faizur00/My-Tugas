package com.example.assignmenttrack.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.assignmenttrack.R
import com.example.assignmenttrack.database.TodoDatabase

class LateTaskWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext = context, params = params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val taskId = inputData.getInt("TASK_ID", -1)

        if (taskId == -1) {
            return Result.failure()
        }

        return try {
            val database = TodoDatabase.getDatabase(applicationContext)
            val taskDao = database.getTaskDao()
            val task = taskDao.getTaskById(taskId)

            if (task != null && task.status == null) { // Check if task is not completed or late
                taskDao.lateTask(taskId = taskId)
                sendLateNotification(task.title)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun sendLateNotification(taskTitle: String) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val channelId = "late_task_channel"

        val channel = NotificationChannel(
            channelId,
            "Late Tasks",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Nawh Dawg, Your Activity for $taskTitle is Late"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.mylogo_foreground)
            .setSmallIcon(R.drawable.my_logo_mono)
            .setContentTitle("Assignment Late: $taskTitle")
            .setContentText("Nawh Dawg, Your Activity for $taskTitle is Late ")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun sendDueTwelveNotification(taskTitle: String) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val channelId = "late_task_channel"

        val channel = NotificationChannel(
            channelId,
            "Late Tasks",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Nawh Dawg, Your Activity for $taskTitle is Late"
        }
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.mylogo_foreground)
            .setSmallIcon(R.drawable.my_logo_mono)
            .setContentTitle("Assignment Due 12 Hours: $taskTitle")
            .setContentText("Don't Forget To Do $taskTitle ma bro")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

