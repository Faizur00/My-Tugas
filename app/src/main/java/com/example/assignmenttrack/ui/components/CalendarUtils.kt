package com.example.assignmenttrack.ui.components

import com.example.assignmenttrack.Model.CalendarMonthData
import java.time.YearMonth
import java.time.LocalDate

object CalendarUtils {

    const val CALENDAR_COLUMNS = 7
    val monthNames = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val dayNames = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")

    // minta bulan ama tahun sebelumnya
    fun getPreviousMonth(currentMonth: Int, currentYear: Int): Pair<Int, Int> {
        val newMonth = if (currentMonth == 1) 12 else currentMonth - 1
        val newYear = if (currentMonth == 1)  currentYear - 1 else currentYear
        return Pair(newMonth, newYear)
    }

    // minta bulan ama tahun setelahnya
    fun getNextMonth(currentMonth: Int, currentYear: Int): Pair<Int, Int> {
        val newMonth = if (currentMonth == 12) 1 else currentMonth + 1
        val newYear = if (currentMonth == 12)  currentYear + 1 else currentYear
        return Pair(newMonth, newYear)
    }

    fun isToday(day: Int, month: Int, year: Int): Boolean {
        val today = java.time.LocalDate.now()
        return  today.dayOfMonth == day &&
                today.monthValue == month &&
                today.year == year
    }

    fun calculateMonthData(year: Int, month: Int): CalendarMonthData{
        val yearMonth = YearMonth.of(year, month)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstDayOfMonth = LocalDate.of(year, month, 1)
        val dayOfWeek = firstDayOfMonth.dayOfWeek.value

        val offset = dayOfWeek - 1
        val previousMonth = yearMonth.minusMonths(1)
        val daysInPreviousMonth = previousMonth.lengthOfMonth()
        val previousMonthStartDay = daysInPreviousMonth - offset + 1

        val totalCells = offset + daysInMonth
        val remainingCells = if (totalCells != 42) 42 - totalCells else 0

        return CalendarMonthData(
            daysInMonth = daysInMonth,
            offset = offset,
            previousMonthStartDay = previousMonthStartDay,
            remainingCells = remainingCells
        )
    }
}