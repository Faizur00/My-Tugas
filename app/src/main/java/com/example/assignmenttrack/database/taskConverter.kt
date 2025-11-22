package com.example.assignmenttrack.database

import androidx.room.TypeConverter
import com.example.assignmenttrack.model.Task
import java.time.Instant

class TaskConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }

    @TypeConverter
    fun fromTaskType(type: Task.TaskType?): String? = type?.name

    @TypeConverter
    fun toTaskType(value: String?): Task.TaskType? = value?.let { Task.TaskType.valueOf(it) }
}
