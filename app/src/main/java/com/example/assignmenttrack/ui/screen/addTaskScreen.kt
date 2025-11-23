package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmenttrack.ui.components.TaskForm
import com.example.assignmenttrack.viewModel.TaskListViewModel


@Composable
fun AddTaskScreen(onTaskSubmit: () -> Unit, taskListViewModel: TaskListViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TaskForm(modifier = Modifier, taskListViewModel = taskListViewModel, onTaskSubmit = onTaskSubmit)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
