package com.example.assignmenttrack

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: Boolean
)

val TaskList = listOf(
    Task("1", "Study Compose", "Learn how to build UI with Jetpack Compose", status = false),
    Task("2", "Do Laundry", "Finish before 6 PM", status = false),
    Task("3", "Read Paper", "Review the CNN architecture paper", status = false),
)