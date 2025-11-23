package com.example.assignmenttrack.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.User

@Database(entities = [Task::class, User::class], version = 2, exportSchema = false)
@TypeConverters(TaskConverter::class)
abstract class TodoDatabase: RoomDatabase(){

    companion object{
        const val NAME = "Todo_DB"
    }

    abstract fun getUserDao(): UserDao
    abstract fun getTaskDao(): TaskDao
}
