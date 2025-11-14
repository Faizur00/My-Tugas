package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.ui.theme.leagueSpartan
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FormField1(
    title: String,
    titleFontWeight: FontWeight,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            title,
            fontFamily = leagueSpartan,
            modifier = Modifier.padding(bottom = 4.dp),
            fontWeight = titleFontWeight
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
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
            modifier = modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun FormField2(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Text(title, fontFamily = leagueSpartan, fontWeight = FontWeight.Medium)

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
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
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .border(3.dp, Color(0xFFCAD6FF), shape = RoundedCornerShape(20.dp))
        )
    }
}

@Composable
fun FormFieldDateTime(
    title: String,
    dateTime: LocalDateTime,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title, fontFamily = leagueSpartan, fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFECF1FF)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onDateClick)
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Select Date",
                            tint = Color.DarkGray
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = dateTime.format(dateFormatter),
                            color = Color.Black
                        )
                    }

                    Divider(
                        color = Color.Gray.copy(alpha = 0.5f),
                        modifier = Modifier
                            .width(1.dp)
                            .height(32.dp)
                    )

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onTimeClick)
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreTime,
                            contentDescription = "Select Time",
                            tint = Color.DarkGray
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = dateTime.format(timeFormatter),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}