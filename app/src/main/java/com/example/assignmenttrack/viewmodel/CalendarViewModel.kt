package com.example.assignmenttrack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.Model.CalendarTask
import com.example.assignmenttrack.Model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import com.example.assignmenttrack.Model.TaskList

class CalendarViewModel: ViewModel(){

    // untuk tahun
    private val _currentYear = MutableStateFlow(java.time.LocalDate.now().year)
    val currentYear: StateFlow<Int> = _currentYear.asStateFlow()

    // untuk bulan
    private val _currentMonth = MutableStateFlow(java.time.LocalDate.now().monthValue)
    val currentMonth: StateFlow<Int> = _currentMonth.asStateFlow()

    private val _currentDay = MutableStateFlow(java.time.LocalDateTime.now().dayOfMonth)
    val currentDay: StateFlow<Int> = _currentDay.asStateFlow()

    // untuk tanggal yang dipilih
    private val _selectedDate = MutableStateFlow<Triple<Int, Int, Int>?>(null)
    val selectedDate: StateFlow<Triple<Int, Int, Int>?> = _selectedDate.asStateFlow()

    // semua task yang dimiliki
    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())
    val allTasks: StateFlow<List<Task>> = _allTasks.asStateFlow()

    // semua task yang ada di kalender di grup berdasarkan tanggal bulan tahun
    private val _calendarTasks = MutableStateFlow<List<CalendarTask>>(emptyList())
    val calendarTasks: StateFlow<List<CalendarTask>> = _calendarTasks.asStateFlow()

    // Semua tugas di tanggal yang di pilih
    private val _selectedDateTasks = MutableStateFlow<List<Task>>(emptyList())
    val selectedDateTasks: StateFlow<List<Task>> = _selectedDateTasks.asStateFlow()

    init {
        loadTask()
    }

    // Ambil semua tugas yang ada
    fun loadTask(){
        viewModelScope.launch {
            val tasks = TaskList  /* TODO: Ganti semua kalau sudah pakai database*/
            _allTasks.value = tasks
            updateCalendarTasks()
        }
    }


    fun onDayClick(day: Int, month: Int, year: Int) {
        viewModelScope.launch {
            _selectedDate.value = Triple(day, month, year)
            updateSelectedDateTasks(selectedDate = _selectedDate.value, calendarTasks = _calendarTasks.value)

            if (month != _currentMonth.value || year != _currentYear.value) {
                changeMonth(month, year)
            }
        }
    }

    fun changeMonth(newMonth: Int, newYear: Int){
        viewModelScope.launch {
            _currentMonth.value = newMonth
            _currentYear.value = newYear
            updateCalendarTasks()
        }
    }

    private fun updateCalendarTasks(){
        val tasks = _allTasks.value

        // di grup berdasarkan tanggalnya
        val calendarTasksMap = tasks.groupBy { task ->
            val localDate = LocalDateTime.ofInstant(
                task.deadline,
                ZoneId.systemDefault()
            )
            Triple(localDate.dayOfMonth,
                localDate.monthValue,
                localDate.year)
        }

        // di saring jadi sesuai dengan bulan
        val filteredTasks = calendarTasksMap
            .filter { (dateTriple, _) ->
                val (_, month, year) = dateTriple
                month == _currentMonth.value && year == _currentYear.value
            }
            .map { (dateTriple, taskList) ->
                val (day, _, _) = dateTriple
                CalendarTask(day = day, task = taskList)
            }

        _calendarTasks.value = filteredTasks
    }

    fun clearSelectedDate(){
        _selectedDate.value = null
    }

    fun updateSelectedDateTasks(
        selectedDate: Triple<Int, Int, Int>? = _selectedDate.value,
        calendarTasks: List<CalendarTask> = _calendarTasks.value
    ){
        selectedDate?.first?.let{ day ->
            _selectedDateTasks.value = calendarTasks.find { it.day == day }?.task ?: emptyList()
        }
    }

    fun updateTaskStatus(){
        /* TODO: Tambah fungsi kalau tugasnya dah selesai berubah warnanya*/
    }
}