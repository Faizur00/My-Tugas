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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.ui.components.Calendar
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.viewModel.CalendarViewModel
import com.example.assignmenttrack.viewModel.TaskListViewModel
import java.time.LocalDate

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
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val calendarTasks by viewModel.calendarTasks.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val selectedDateTriple by viewModel.selectedDateTriple.collectAsStateWithLifecycle()
    val selectedDateTasks by viewModel.selectedDateTasks.collectAsStateWithLifecycle()
    val taskListViewModel: TaskListViewModel = hiltViewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                CalendarScreenHeader()
                Spacer(modifier = Modifier.height(16.dp))

                // Calendar component
                Calendar(
                    modifier = Modifier
                        .height(275.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    calendarInput = calendarTasks,
                    year = selectedDate?.year ?: LocalDate.now().year,
                    month = selectedDate?.monthValue ?: LocalDate.now().monthValue,
                    onDayClick = { day, month, year ->
                        val date = LocalDate.of(year, month, day)
                        viewModel.setSelectedDate(date)
                    },
                    onMonthChange = { newMonth, newYear ->
                        val date = selectedDate ?: LocalDate.now()
                        viewModel.setSelectedDate(LocalDate.of(newYear, newMonth, date.dayOfMonth))
                    }
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                ) {
                    // Pesan jika tidak ada task atau tanggal belum dipilih
                    DateText(
                        modifier = Modifier,
                        selectedDate = selectedDateTriple,
                        selectedDateTasks = selectedDateTasks
                    )

                    val taskListViewModel: TaskListViewModel = viewModel() // kalau mau interaksi dengan TaskListViewModel
                    TaskList(
                        modifier = Modifier,
                        tasks = selectedDateTasks,
                        taskListViewModel = taskListViewModel
                    )

                    // Gradien atas
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