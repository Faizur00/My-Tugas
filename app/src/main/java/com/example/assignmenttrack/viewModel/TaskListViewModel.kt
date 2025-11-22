package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel(){

    // Expose tasks dari repository
    val tasks: Flow<List<Task>> = repository.getAllTasks()

    // Tambah task
    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }

    // Hapus task
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }

    // Tandai task selesai
    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            repository.completeTask(taskId)
        }
    }
}