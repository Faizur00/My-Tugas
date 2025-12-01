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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun CalendarRoute(onBackClick: () -> Unit){
    Surface(
        color = Color(0xFFCAD6FF),
        modifier = Modifier
            .fillMaxSize()
    ) {
        CalendarScreen(
            modifier = Modifier
                .fillMaxWidth(),
            onBackClick = onBackClick
        )
    }
}

// malas ngedebug
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val calendarTasks by viewModel.calendarTasks.collectAsStateWithLifecycle()
    val selectedDateTriple by viewModel.selectedDateTriple.collectAsStateWithLifecycle()
    val selectedDateTasks by viewModel.selectedDateTasks.collectAsStateWithLifecycle()
    val taskListViewModel: TaskListViewModel = hiltViewModel()
    val selectedMonth by viewModel.selectedMonth.collectAsStateWithLifecycle()
    val selectedYear by viewModel.selectedYear.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFCAD6FF)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                CalendarScreenHeader(onBackClick = onBackClick)
                Spacer(modifier = Modifier.height(16.dp))

                // Calendar component
                Calendar(
                    modifier = Modifier
                        .height(275.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    calendarInput = calendarTasks,
                    year = selectedYear,
                    month = selectedMonth,
                    onDayClick = { day, monthClicked, yearClicked ->
                        if (monthClicked != selectedMonth || yearClicked != selectedYear) {
                            viewModel.changeMonth(monthClicked, yearClicked)
                        }
                        val date = LocalDate.of(yearClicked, monthClicked, day)
                        viewModel.setSelectedDate(date)
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
                ) {
                    // Pesan jika tidak ada task atau tanggal belum dipilih
                    DateText(
                        modifier = Modifier,
                        selectedDate = selectedDateTriple,
                        selectedDateTasks = selectedDateTasks
                    )

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
fun CalendarScreenHeader(onBackClick: () -> Unit){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                 Icon(
                     imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                     contentDescription = "Balik",
                     tint = Color(0xFF2260FF)
                 )
             }

            Text(
                text = "My Kalender",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
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