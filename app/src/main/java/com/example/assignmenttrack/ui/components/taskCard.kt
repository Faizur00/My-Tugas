package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.ui.theme.leagueSpartan
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// single card task
@Composable
fun TaskCard(task: Task) {
    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy - hh:mm a")
        .withZone(ZoneId.systemDefault())
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.deadline
                        .atZone(ZoneId.systemDefault())
                        .format(formatter),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp),
                    color = Color(0xFF2260FF),
                    fontWeight = FontWeight.Medium,
                )

                Column {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(color = Color(0xFF2260FF)),
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "More options",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        Modifier
                            .background(color = Color.White)
                            .border(1.dp, Color(0xFF84A0FA), RoundedCornerShape(4.dp))
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit", color = Color(0xFF728FFC)) },
                            onClick = { /* TODO: Handle edit */ },
                            leadingIcon = {Icon(imageVector = Icons.Default.Edit, tint = Color(0xFF456DEE), contentDescription = "Edit")}
                        )

                        HorizontalDivider(color = Color(0xFF84A0FA))

                        DropdownMenuItem(
                            text = { Text("Complete", color = Color(0xFF728FFC)) },
                            onClick = { /* TODO: Handle complete */ },
                            leadingIcon = {Icon(imageVector = Icons.Default.CheckCircle, tint = Color(0xFF456DEE), contentDescription = "Complete")}
                        )

                        HorizontalDivider(color = Color(0xFF84A0FA))

                        DropdownMenuItem(
                            text = { Text("Delete", color = Color(0xFF728FFC)) },
                            onClick = { /* TODO: Handle delete */ },
                            leadingIcon = {Icon(imageVector = Icons.Default.Delete, tint = Color(0xFF456DEE), contentDescription = "Delete")}
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFCAD6FF)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = task.type.toString(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF2260FF),
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = leagueSpartan
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = leagueSpartan,
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontFamily = leagueSpartan
                    )
                }
            }
        }
    }
}
