package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmenttrack.ui.components.Calendar
import com.example.assignmenttrack.ui.components.CalendarUtils
import com.example.assignmenttrack.ui.components.TaskCard
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.viewmodel.CalendarViewModel

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

    Column(modifier = modifier.fillMaxWidth()) {
        // Center the Calendar at the top
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "My Kalender",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                fontFamily = leagueSpartan,
                color = Color(0xFF2260FF),
            )

            Calendar(
                modifier = Modifier
                    .height(270.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 128.dp)
        ) {
            item {
                selectedDate?.let { (day, month, year) ->
                    Spacer(Modifier.height(16.dp))
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

            if (selectedDate != null && selectedDateTasks.isEmpty()) {
                item {
                    Text(
                        text = "There is no task",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                items(
                    items = selectedDateTasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskCard(task, modifier = Modifier.fillMaxWidth(0.9f))
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}
