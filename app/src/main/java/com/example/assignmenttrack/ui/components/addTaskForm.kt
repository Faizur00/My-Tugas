package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.viewModel.TaskListViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskForm(modifier: Modifier = Modifier, taskListViewModel: TaskListViewModel, onTaskSubmit: () -> Unit) {
    var assignmentType by remember { mutableStateOf(Task.TaskType.Belajar) }
    var assignmentTitle by remember { mutableStateOf("") }
    var assignmentDescription by remember { mutableStateOf("") }

    // Store input form for Date amd time
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            FormFieldDropdown(
                title = "Assignment Type",
                titleFontWeight = FontWeight.Medium,
                selectedType = assignmentType,
                onTypeSelected = { assignmentType = it }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormField1(
                title = "Assignment Title",
                value = assignmentTitle,
                onValueChange = { assignmentTitle = it },
                titleFontWeight = FontWeight.Medium,
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormFieldDateTime(
                title = "Due Date & Time",
                dateTime = selectedDateTime,
                onDateClick = { showDatePicker = true },
                onTimeClick = { showTimePicker = true }
            )
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            FormField2(
                title = "Description",
                value = assignmentDescription,
                onValueChange = { assignmentDescription = it }
            )
        }


        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            GeneralSubmitButton(
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .fillMaxWidth(1f)
                    .padding(all = 16.dp),
                text = "Tambah Tugas",
                onClick = {
                    val newTask = Task(
                        type = assignmentType,
                        title = assignmentTitle,
                        description = assignmentDescription,
                        status = null,
                        deadline = selectedDateTime.atZone(ZoneId.systemDefault()).toInstant()
                    )
                    taskListViewModel.addTask(newTask)
                    onTaskSubmit()
                }
            )
        }
    }


    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDateTime
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        selectedDateTime = selectedDateTime.with(selectedDate)
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = Color.DarkGray)
                }
            },
            shape = RoundedCornerShape(20.dp),
            tonalElevation = 0.dp,
            colors = DatePickerDefaults.colors(
                containerColor = Color(0xFFECF1FF)
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = Color(0xFFECF1FF),
                    titleContentColor = Color.Black,
                    headlineContentColor = Color.Black,
                    weekdayContentColor = Color(0xFF3B82F6),
                    dayContentColor = Color.Black,
                    selectedDayContentColor = Color(0xFF3B82F6),
                    selectedDayContainerColor = Color.White,
                    todayContentColor = Color(0xFF3B82F6),
                    todayDateBorderColor = Color(0xFF3B82F6),
                    yearContentColor = Color.Black,
                    selectedYearContainerColor = Color(0xFF3B82F6),
                    selectedYearContentColor = Color.White,
                    currentYearContentColor = Color(0xFF3B82F6),
                    navigationContentColor = Color.Black,
                    dateTextFieldColors = OutlinedTextFieldDefaults
                        .colors(
                            unfocusedTextColor = Color.Black,
                            focusedTextColor = Color.Black,
                            unfocusedBorderColor = Color(0xFF3B82F6),
                            focusedBorderColor = Color(0xFF3B82F6),
                            unfocusedLabelColor = Color.Black,
                            focusedLabelColor = Color(0xFF3B82F6),
                            cursorColor = Color(0xFF3B82F6),
                            selectionColors = TextSelectionColors(
                                handleColor = Color(0xFF3B82F6),
                                backgroundColor = Color(0xFF3B82F6).copy(alpha = 0.4f)
                            )
                        )
                )
            )
        }
    }

// Time Picker Dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedDateTime.hour,
            initialMinute = selectedDateTime.minute,
            is24Hour = false
        )

        Dialog(onDismissRequest = { showTimePicker = false }) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFECF1FF)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = Color.White,
                            clockDialSelectedContentColor = Color.White,
                            selectorColor = Color(0xFF3B82F6),
                            containerColor = Color.Transparent,
                            periodSelectorBorderColor = Color.Gray,
                            periodSelectorSelectedContainerColor = Color(0xFF3B82F6),
                            periodSelectorUnselectedContainerColor = Color.Transparent,
                            periodSelectorSelectedContentColor = Color.White,
                            periodSelectorUnselectedContentColor = Color.DarkGray,
                            timeSelectorSelectedContainerColor = Color(0xFF3B82F6),
                            timeSelectorUnselectedContainerColor = Color.White,
                            timeSelectorSelectedContentColor = Color.White,
                            timeSelectorUnselectedContentColor = Color.Black,
                            clockDialUnselectedContentColor = Color.Black
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showTimePicker = false }) {
                            Text("Cancel", color = Color.DarkGray)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            selectedDateTime = selectedDateTime.with(selectedTime)
                            showTimePicker = false
                        }) {
                            Text("OK", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

//private fun generateNewId(): Int {
//    return (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
//}