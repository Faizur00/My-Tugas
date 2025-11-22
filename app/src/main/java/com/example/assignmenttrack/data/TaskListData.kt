package com.example.assignmenttrack.data

import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.createInstant
import kotlinx.serialization.Serializable
import java.time.Month

@Serializable
val TaskList = listOf(
    Task(
        id = 1,
        type = Task.TaskType.Belajar,
        title = "Study Compose",
        description = "Learn how to build UI with Jetpack Compose",
        status = false,
        // Deadline: Today (Nov 12), 10:00 PM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 12, 22, 30)
    ),
    Task(
        id = 2,
        type = Task.TaskType.Kerja,
        title = "Do Laundry",
        description = "Finish before 6 PM",
        status = false,
        // Deadline: Today (Nov 12), 6:00 PM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 12, 18, 27)
    ),
    Task(
        id = 3,
        type = Task.TaskType.Belajar,
        title = "Read Paper",
        description = "Review the CNN architecture paper",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 32)
    ),
    Task(
        id = 4,
        type = Task.TaskType.Tugas,
        title = "OOP Homework",
        description = "Defeat dewa babylonia",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 21)
    ),
    Task(
        id = 5,
        type = Task.TaskType.Tugas,
        title = "tamatkan silksong",
        description = "Defeat dewa lastboss",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 15, 9, 21)
    ),
    Task(
        id = 6,
        type = Task.TaskType.Kerja,
        title = "Gacha Haruka",
        description = "Kumpul Originium",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 21)
    ),
    Task(
        id = 7,
        type = Task.TaskType.Kerja,
        title = "Gacha Cyrene",
        description = "Kumpul Stellar Jade",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.NOVEMBER, 13, 9, 21)
    ),
    Task(
        id = 8,
        type = Task.TaskType.Belajar,
        title = "Lihat Meta di HSR",
        description = "Gacha tim meta",
        status = false,
        // Deadline: Tomorrow (Nov 13), 9:30 AM WITA
        deadline = createInstant(2025, Month.DECEMBER, 1, 9, 21)
    ),
)