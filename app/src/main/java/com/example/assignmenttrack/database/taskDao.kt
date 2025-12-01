package com.example.assignmenttrack.database;

import androidx.room.*;
import com.example.assignmenttrack.model.Stat
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

    @Query("DELETE FROM Tasks WHERE id = :taskId ")
    suspend fun deleteTask(taskId: Int)

    @Query("""
        SELECT 
            SUM(CASE WHEN status = 1 THEN 1 ELSE 0 END) AS taskCompleted,
            SUM(CASE WHEN status = 0 AND deadline < :now THEN 1 ELSE 0 END) AS taskLate,
            SUM(CASE WHEN status IS NULL THEN 1 ELSE 0 END) AS taskPending,
            SUM(CASE WHEN type = 'Tugas' AND status = 1 THEN 1 ELSE 0 END) AS tugasTotal,
            SUM(CASE WHEN type = 'Kerja' AND status = 1 THEN 1 ELSE 0 END) AS kerjaTotal,
            SUM(CASE WHEN type = 'Belajar' AND status = 1 THEN 1 ELSE 0 END) AS belajarTotal,
            COUNT(*) AS taskTotal
        FROM Tasks
    """) // Hitung semua untuk stat
    fun getStat(now: Long): Flow<Stat>

    @Query("""
        SELECT * FROM Tasks 
        WHERE strftime('%m', datetime(deadline / 1000, 'unixepoch')) = :month
          AND strftime('%Y', datetime(deadline / 1000, 'unixepoch')) = :year
        ORDER BY deadline ASC
    """) // Filter bulan dan tahun
    fun getTasksByMonth(month: String, year: String): Flow<List<Task>>

    @Query("""
    SELECT * FROM Tasks 
    WHERE strftime('%Y-%m-%d', datetime(deadline / 1000, 'unixepoch')) = 
          strftime('%Y-%m-%d', datetime(:date / 1000, 'unixepoch'))
    """) // Filter tanggal
    fun getTasksByDate(date: Long): Flow<List<Task>>
}
