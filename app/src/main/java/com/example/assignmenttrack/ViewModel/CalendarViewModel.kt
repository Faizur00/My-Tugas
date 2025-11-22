package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.uiStateData.CalendarTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    // StateFlow untuk tanggal yang dipilih
    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    // Ambil semua task dari repository
    private val allTasks: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val selectedDateTriple: StateFlow<Triple<Int, Int, Int>?> =
        selectedDate.map { date ->
            date?.let { Triple(it.dayOfMonth, it.monthValue, it.year) }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    // CalendarTask: group tasks berdasarkan tanggal
    val calendarTasks: StateFlow<List<CalendarTask>> =
        allTasks.map { tasks ->
            tasks.groupBy { it.deadline.atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth }
                .map { (day, tasksForDay) ->
                    CalendarTask(day = day, tasks = tasksForDay)
                }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Filter task berdasarkan tanggal yang dipilih
    val selectedDateTasks: StateFlow<List<Task>> =
        combine(allTasks, _selectedDate) { tasks, date ->
            if (date == null) emptyList()
            else tasks.filter {
                val taskDate = it.deadline.atZone(ZoneId.systemDefault()).toLocalDate()
                taskDate == date
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Set tanggal yang dipilih di kalender
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    // Hapus task lewat repository
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    // Tandai task selesai lewat repository
    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            repository.completeTask(taskId)
        }
    }
}