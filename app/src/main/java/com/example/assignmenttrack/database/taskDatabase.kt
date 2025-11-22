package com.example.assignmenttrack.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignmenttrack.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskConverter::class)
abstract class TaskDatabase: RoomDatabase(){

    companion object{
        const val NAME = "Todo_DB"
    }

    abstract fun getTaskDao(): TaskDao
}
