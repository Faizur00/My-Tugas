package com.example.assignmenttrack.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.data.TaskList
import com.example.assignmenttrack.ui.components.GeneralSubmitButton
import com.example.assignmenttrack.ui.components.ProfileSection
import com.example.assignmenttrack.ui.components.TaskCard

@Composable
fun MainDashboard(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ProfileSection(name = "Faiz")
                TaskListScreen()
            }
            GeneralSubmitButton(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.9f)
                    .padding(all = 24.dp),
                onClick = onAddTaskClick,
                text = "Tugas Baru"
            )
        }
    }
}


// handle showing list of card task (emg nge lag pas masih debug)
@Composable
fun TaskListScreen(modifier: Modifier = Modifier) {
    var taskList by remember { mutableStateOf(TaskList) }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 32.dp)
    ){
        items(items = taskList, key = { task -> task.id }) { task ->
            TaskCard(task)
            Spacer(Modifier.height(16.dp))
        }

    }
}