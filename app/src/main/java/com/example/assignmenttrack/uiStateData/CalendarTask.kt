package com.example.assignmenttrack.uiStateData

import com.example.assignmenttrack.model.Task

data class CalendarTask(
    val day:Int,
    val tasks:List<Task>? = null
)

