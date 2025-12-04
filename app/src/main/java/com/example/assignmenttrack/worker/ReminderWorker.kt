package com.example.assignmenttrack.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.assignmenttrack.R
import com.example.assignmenttrack.database.TodoDatabase

class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext = context, params = params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val taskId = inputData.getInt("TASK_ID", -1)
        val taskTitle = inputData.getString("TASK_TITLE") ?: ""
        val taskDescription = inputData.getString("TASK_DESC") ?: ""

        if (taskId == -1) {
            return Result.failure()
        }

        return try {
            sendNotification(taskTitle, taskDescription)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun sendNotification(taskTitle: String, taskDescription: String) {
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val channelId = "task_reminder_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Task Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications to remind you of upcoming tasks."
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.mylogo_foreground) // Replace with a real icon
            .setSmallIcon(R.drawable.my_logo_mono)
            .setContentTitle("Incoming Deadline!!!. $taskTitle Due To 12 Hours")
            .setContentText(taskDescription)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
