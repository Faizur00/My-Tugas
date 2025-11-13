package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
//import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material3.ExperimentalMaterial3Api
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TaskForm(modifier: Modifier = Modifier){
    var assignmentType by remember { mutableStateOf("") }
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
            //        Jenis kegiatan
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Jenis Kegiatan",color = Color.Black)
                TextField(
                    value = assignmentType,
                    onValueChange = { assignmentType = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFECF1FF),
                        unfocusedContainerColor = Color(0xFFECF1FF),
                        disabledContainerColor = Color(0xFFECF1FF),
                        errorContainerColor = Color(0xFFECF1FF),
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            //        judul kegiatan
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Judul Kegiatan", color = Color.Black)
                TextField(
                    value = assignmentTitle,
                    onValueChange = { assignmentTitle = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFECF1FF),
                        unfocusedContainerColor = Color(0xFFECF1FF),
                        disabledContainerColor = Color(0xFFECF1FF),
                        errorContainerColor = Color(0xFFECF1FF),
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            //        handle pilih tanggal dan waktu
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Deadline Tanggal",color = Color.Black)
                TextField(
                    value = selectedDateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    onValueChange = { /* Read-only */ },
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFECF1FF),
                        unfocusedContainerColor = Color(0xFFECF1FF),
                        disabledContainerColor = Color(0xFFECF1FF),
                        errorContainerColor = Color(0xFFECF1FF),
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .clickable { showDatePicker = true },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Select Date",
                            tint = Color(0xFF2260FF)
                        )
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(8.dp))
        }

        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Deadline Waktu",color = Color.Black)
                TextField(
                    value = selectedDateTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                    onValueChange = { /* Read-only */ },
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFECF1FF),
                        unfocusedContainerColor = Color(0xFFECF1FF),
                        disabledContainerColor = Color(0xFFECF1FF),
                        errorContainerColor = Color(0xFFECF1FF),
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .clickable { showTimePicker = true },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.MoreTime,
                            contentDescription = "Select Time",
                            tint = Color(0xFF2260FF)
                        )
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(16.dp))
        }

        item {
            //        Deskripsi kegiatan
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Deskripsi Kegiatan", color = Color.Black)
                OutlinedTextField(
                    value = assignmentDescription,
                    onValueChange = { assignmentDescription = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent,
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 200.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .border(3.dp, Color(0xFFCAD6FF), shape = RoundedCornerShape(20.dp))
                )
            }
        }
    }
}