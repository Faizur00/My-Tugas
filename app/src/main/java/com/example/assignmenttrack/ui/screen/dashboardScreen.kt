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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assignmenttrack.ui.components.GeneralSubmitButton
import com.example.assignmenttrack.ui.components.ProfileSection
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.viewmodel.CalendarViewModel
import com.example.assignmenttrack.ui.components.Calendar
import com.example.assignmenttrack.ui.components.CalendarUtils
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.Model.defaultUser

@Composable
fun MainDashboard(
    modifier: Modifier = Modifier,
    onAddTaskClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onStatClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ->
                ProfileSection(name = defaultUser.name, onStatClick = onStatClick, onProfileClick = onProfileClick) // ganti mi kalau sudah ku isi2 ji
                CalendarScreen(
                    modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
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


// malas ngedebug
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = viewModel()
) {
    val currentYear by viewModel.currentYear.collectAsStateWithLifecycle()
    val currentMonth by viewModel.currentMonth.collectAsStateWithLifecycle()
    val calendarTasks by viewModel.calendarTasks.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedDateTasks by viewModel.selectedDateTasks.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        item {
            Calendar(
                modifier = Modifier.height(270.dp),
                calendarInput = calendarTasks,
                year = currentYear,
                month = currentMonth,
                onDayClick = { day, month, year ->
                    viewModel.onDayClick(day, month, year)
                    viewModel.updateSelectedDateTasks(Triple(day, month, year), calendarTasks)
                },
                onMonthChange = { newMonth, newYear ->
                    viewModel.changeMonth(newMonth, newYear)
                }
            )
        }

        item {
            selectedDate?.let { (day, month, year) ->
                Text(
                    text = "$day ${CalendarUtils.monthNames[month - 1]} $year",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF2260FF),
                    textAlign = TextAlign.Center
                )
            } ?: run {
                Text(
                    text = "Select a date to view tasks",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }

        if(selectedDate != null && selectedDateTasks.isEmpty()){
            item {
                Text(
                    text = "There is no task",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
        else{
            items(
                items = selectedDateTasks,
                key = { task -> task.id }
            ) { task ->
                TaskCard(task)
                Spacer(Modifier.height(16.dp))
            }
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
            TaskCard(task)
            Spacer(Modifier.height(16.dp))
        }
    }
}


