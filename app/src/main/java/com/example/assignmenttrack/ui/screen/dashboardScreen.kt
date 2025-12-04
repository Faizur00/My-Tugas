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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.ui.components.GeneralSubmitButton
import com.example.assignmenttrack.ui.components.ProfileSection
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.viewModel.TaskListViewModel


@Composable
fun MainDashboard(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onStatClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onEditClick : (Task) -> Unit = {},
    taskListViewModel: TaskListViewModel
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ProfileSection(onStatClick = onStatClick, onProfileClick = onProfileClick, onCalendarClick = onCalendarClick)
                TaskListScreen(taskListViewModel, onEditClick = onEditClick)
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

@Composable
fun TaskListScreen(
    taskViewModel: TaskListViewModel,
    modifier: Modifier = Modifier,
    onEditClick : (Task) -> Unit = {}
) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 128.dp)
    ){
        val filteredTasks = tasks.filter { it.status == null }

        if (filteredTasks.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Ongoing Tasks",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        } else {
            items(items = filteredTasks, key = { task -> task.id }) { task ->
                TaskCard(task, modifier = Modifier, taskViewModel, onEditClick = onEditClick)
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}