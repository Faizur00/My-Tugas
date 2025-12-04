package com.example.assignmenttrack.model

data class CalendarTask(
    val taskByDay: Map<Int, List<Task>>
)
