package com.example.assignmenttrack.database

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.worker.LateTaskWorker
import com.example.assignmenttrack.worker.ReminderWorker
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit


class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(taskId: Int) = taskDao.deleteTask(taskId)

    suspend fun completeTask(taskId: Int) = taskDao.completeTask(taskId)

    suspend  fun lateTask(taskId: Int) = taskDao.lateTask(taskId)

    fun getTasksByMonth(month: Int, year: Int): Flow<List<Task>> {
        val monthStr = "%02d".format(month)
        val yearStr = year.toString()
        return taskDao.getTasksByMonth(monthStr, yearStr)
    }

    fun getTasksByDate(date: Long): Flow<List<Task>> {
        return taskDao.getTasksByDate(date)
    }

    fun scheduleLateCheck(context: Context, task: Task){
        val workManager = WorkManager.getInstance(context)

        val currentTime = Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val deadlineTime = task.deadline.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val delay = deadlineTime - currentTime

        if (delay < 0){
            return
        }

        val inputData = workDataOf("TASK_ID" to task.id)
        val uniqueTag = "late_check_${task.id}"

        val lateRequest = OneTimeWorkRequest.Builder(LateTaskWorker::class.java)
            .setInitialDelay(delay, timeUnit = TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag(uniqueTag)
            .build()


        workManager.enqueueUniqueWork(
            uniqueTag,
            ExistingWorkPolicy.REPLACE,
            lateRequest
        )

        scheduleReminder(context, task)
    }

    private fun scheduleReminder(context: Context, task: Task) {
        val workManager = WorkManager.getInstance(context)

        val currentTime = Instant.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val deadlineTime = task.deadline.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val reminderTime = deadlineTime - TimeUnit.HOURS.toMillis(12)
        val delay = reminderTime - currentTime

        if (delay < 0) {
            return
        }

        val inputData = workDataOf(
            "TASK_ID" to task.id,
            "TASK_TITLE" to task.title,
            "TASK_DESC" to task.description
        )
        val uniqueTag = "reminder_${task.id}"

        val reminderRequest = OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag(uniqueTag)
            .build()

        workManager.enqueueUniqueWork(
            uniqueTag,
            ExistingWorkPolicy.REPLACE,
            reminderRequest
        )
    }


    fun cancelLateCheck(context: Context, taskId: Int) {
        val workManager = WorkManager.getInstance(context)
        val uniqueTag = "late_check_${taskId}"
        val reminderTag = "reminder_${taskId}"

        workManager.cancelAllWorkByTag(uniqueTag)
        workManager.cancelAllWorkByTag(reminderTag)
    }
}