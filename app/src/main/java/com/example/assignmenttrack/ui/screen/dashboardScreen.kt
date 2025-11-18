package com.example.assignmenttrack.ui.screen

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.ui.components.GeneralSubmitButton
import com.example.assignmenttrack.ui.components.ProfileSection
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.Model.TaskList
import com.example.assignmenttrack.Model.defaultUser

@Composable
fun MainDashboard(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onStatClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ->
                ProfileSection(name = defaultUser.name, onStatClick = onStatClick, onProfileClick = onProfileClick, onCalendarClick = onCalendarClick)
                TaskListScreen(Modifier, TaskList)
            }
            GeneralSubmitButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(1f)
                    .padding(all = 32.dp),
                onClick = onAddTaskClick,
                text = "Tugas Baru"
            )
        }
    }
}



// handle showing list of card task (emg nge lag pas masih debug)
@Composable
fun TaskListScreen(modifier: Modifier = Modifier, tasks: List<Task>) {
    var taskList by remember { mutableStateOf(tasks) }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ){
        items(items = taskList, key = { task -> task.id }) { task ->
            TaskCard(task, modifier = Modifier)
            Spacer(Modifier.height(16.dp))
        }
    }
}


