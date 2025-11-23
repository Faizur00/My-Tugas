package com.example.assignmenttrack.database;

import androidx.room.*;
import com.example.assignmenttrack.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("SELECT * FROM Tasks ORDER BY deadline ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("UPDATE Tasks SET status = 1 WHERE id = :taskId")
    suspend fun completeTask(taskId: Int)

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun insertTask(task: Task)

    @Query("DELETE FROM TASKS WHERE id = :taskId ")
    suspend fun deleteTask(taskId: Int)

//    @Query() Untuk Filter Task
//    fun getTaskByMonthYear()
}
