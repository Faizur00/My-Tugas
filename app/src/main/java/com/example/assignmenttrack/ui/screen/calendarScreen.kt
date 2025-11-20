package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.ui.components.Calendar
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.viewModel.CalendarViewModel
import com.example.assignmenttrack.viewModel.TaskListViewModel

@Composable
fun CalendarRoute(){
    Surface(
        color = Color(0xFFCAD6FF),
        modifier = Modifier
            .fillMaxSize()
    ) {
        CalendarScreen(
            modifier = Modifier
                .fillMaxWidth()
        )
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

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                CalendarScreenHeader()

                Spacer(modifier = Modifier.height(16.dp))

                Calendar(
                    modifier = Modifier
                        .height(275.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    calendarInput = calendarTasks,
                    year = currentYear,
                    month = currentMonth,
                    onDayClick = { day, month, year ->
                        viewModel.onDayClick(day, month, year)
                        viewModel.loadSelectedDateTasks(Triple(day, month, year), calendarTasks)
                    },
                    onMonthChange = { newMonth, newYear ->
                        viewModel.changeMonth(newMonth, newYear)
                    }
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ){
                    DateText(
                        modifier = Modifier,
                        selectedDate = selectedDate,
                        selectedDateTasks = selectedDateTasks
                    )

                    val taskListViewModel: TaskListViewModel = viewModel()

                    TaskList(
                        modifier = Modifier,
                        tasks = selectedDateTasks,
                        taskListViewModel = taskListViewModel
                    )

                    Box(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFCAD6FF),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarScreenHeader(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "My Kalender",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = leagueSpartan,
                color = Color(0xFF2260FF)
            )
        }
    }
}

@Composable
fun TaskList(modifier: Modifier = Modifier, tasks: List<Task>, taskListViewModel: TaskListViewModel) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 42.dp),
    ){
        items(items = tasks, key = { it.id }) { task ->
            TaskCard(task, modifier = Modifier.fillMaxWidth(), taskListViewModel =  taskListViewModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DateText(modifier: Modifier, selectedDate: Triple<Int, Int, Int>?, selectedDateTasks: List<Task>){
    val message = when {
        selectedDate == null -> "Select a Date to view Tasks"
        selectedDateTasks.isEmpty() -> "There is no Task"
        else -> null
    }

    if (message != null) {
        Text(
            text = message,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}