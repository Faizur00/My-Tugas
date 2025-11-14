package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.data.Task
import java.time.format.DateTimeFormatter

// single card task
@Composable
fun TaskCard(task: Task) {
    // Define a formatter for the deadline
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)), // Apply clip to the Card itself for proper shadow rendering
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.deadline.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(start = 4.dp),
                    color = Color(0xFF2260FF),
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = { /* Handle delete action */ }
                ) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = Color.Gray)
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFCAD6FF)
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 4.dp, bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = task.type,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF2260FF)
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy((-8).dp)
                        ) {
                            IconButton(
                                onClick = { /* Handle delete action */ }

                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        tint = Color(0xFF2260FF),
                                        modifier = Modifier
                                            .padding(3.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = { /* Handle delete action */ }

                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Check",
                                        tint = Color(0xFF2260FF),
                                        modifier = Modifier
                                            .padding(3.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
