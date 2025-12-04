package com.example.assignmenttrack.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val db: TaskRepository,
    @ApplicationContext private val context: Context
    ) : ViewModel(){

    // Expose tasks dari repository
    val tasks: Flow<List<Task>> = db.getAllTasks()


    // Tambah task
    fun addTask(task: Task) {
        val newId = Random.nextInt(1, Int.MAX_VALUE)
        val finalTask = task.copy(id = newId)
        viewModelScope.launch {
            db.insertTask(finalTask)
        }
        db.scheduleLateCheck(context, task = finalTask)
    }

    // Hapus task
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            db.deleteTask(taskId)
        }
        db.cancelLateCheck(context,taskId)
    }

    // Tandai task selesai
    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            db.completeTask(taskId)
        }
    }

    fun lateTask(taskId: Int){
        viewModelScope.launch{
            db.lateTask(taskId)
        }
    }
}
