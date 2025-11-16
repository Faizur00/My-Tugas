package com.example.assignmenttrack.data

data class CalendarMonthData(
    val daysInMonth: Int,
    val offset: Int,
    val previousMonthStartDay: Int,
    val remainingCells: Int
)
