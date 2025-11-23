package com.example.assignmenttrack.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var type: TaskType,
    var title: String,
    var description: String,
    var deadline: Instant,
    var status: Boolean? = null
) {
    enum class TaskType {
        Tugas,
        Kerja,
        Belajar
    }
}

//fun createInstant(year: Int, month: Month, day: Int, hour: Int, minute: Int): Instant {
//    val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
//    return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
//}