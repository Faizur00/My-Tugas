package com.example.assignmenttrack.Model

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.Instant
import java.time.Month

enum class TaskType {
    Tugas,
    Kerja,
    Belajar
}

data class Task(
    val id: String,
    val type: TaskType,
    val title: String,
    val description: String,
    val deadline: Instant,
    val status: Boolean
)

fun createInstant(year: Int, month: Month, day: Int, hour: Int, minute: Int): Instant {
    val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
}


val TaskList = listOf(
    Task(
        id = "1",
        type = TaskType.Belajar,
        title = "Study Compose",
        description = "Learn how to build UI with Jetpack Compose",
        status = false,
        // Deadline: Today (Nov 12), 10:00 PM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 12, 22, 30)
    ),
    Task(
        id = "2",
        type = TaskType.Kerja,
        title = "Do Laundry",
        description = "Finish before 6 PM",
        status = false,
        // Deadline: Today (Nov 12), 6:00 PM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 12, 18, 27)
    ),
    Task(
        id = "3",
        type = TaskType.Belajar,
        title = "Read Paper",
        description = "Review the CNN architecture paper",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 32)
    ),
    Task(
        id = "4",
        type = TaskType.Tugas,
        title = "OOP Homework",
        description = "Defeat dewa babylonia",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 21)
    ),
    Task(
        id = "5",
        type = TaskType.Tugas,
        title = "tamatkan silsong",
        description = "Defeat dewa lastboss",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 15, 9, 21)
    ),
)

