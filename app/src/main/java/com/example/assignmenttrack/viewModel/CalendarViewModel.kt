package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.CalendarTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    private val _selectedMonth = MutableStateFlow(LocalDate.now().monthValue)
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _selectedYear = MutableStateFlow(LocalDate.now().year)
    val selectedYear = _selectedYear.asStateFlow()

    private val _selectedDateTriple = MutableStateFlow<Triple<Int, Int, Int>?>(null)
    val selectedDateTriple: StateFlow<Triple<Int, Int, Int>?> = _selectedDateTriple.asStateFlow()

    private val _selectedDateTasks = MutableStateFlow<List<Task>>(emptyList())
    val selectedDateTasks: StateFlow<List<Task>> = _selectedDateTasks.asStateFlow()

    private val _calendarTasks = MutableStateFlow<List<CalendarTask>>(emptyList())
    val calendarTasks: StateFlow<List<CalendarTask>> = _calendarTasks.asStateFlow()


    init {
        refreshCalendarTasks(
            _selectedMonth.value,
            _selectedYear.value)
    }
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
        _selectedDateTriple.value = Triple(date.dayOfMonth, date.monthValue, date.year)

        val millis = date.atStartOfDay(ZoneOffset.UTC)
            .toInstant().toEpochMilli()

        viewModelScope.launch {
            repository.getTasksByDate(millis).collect { list ->
                _selectedDateTasks.value = list
            }
        }
    }

    fun refreshCalendarTasks(month: Int, year: Int) {
        viewModelScope.launch {
            repository.getTasksByMonth(month, year).collect { tasks ->

                val grouped = tasks.groupBy {
                    it.deadline.atZone(ZoneId.systemDefault())
                        .toLocalDate().dayOfMonth
                }.map { (day, tasksForDay) ->
                    CalendarTask(day, tasksForDay)
                }

                _calendarTasks.value = grouped
            }
        }
    }

    fun changeMonth(newMonth: Int, newYear: Int) {
        _selectedMonth.value = newMonth
        _selectedYear.value = newYear

        _selectedDate.value = null
        _selectedDateTriple.value = null
        _selectedDateTasks.value = emptyList()

        refreshCalendarTasks(newMonth, newYear)
    }
}