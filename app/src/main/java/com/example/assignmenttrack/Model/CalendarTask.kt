package com.example.assignmenttrack.Model

data class CalendarTask(
    val day:Int,
    val task:List<Task>? = null
)

