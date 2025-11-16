package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.assignmenttrack.ui.components.StatCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.Model.defaultUser
import com.example.assignmenttrack.ui.theme.leagueSpartan
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatScreen(){
    Surface(color = Color(0xFFCAD6FF)){
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Statistik Aktivitas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = leagueSpartan,
                    color = Color(0xFF2260FF),
                    modifier = Modifier.padding(top = 16.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 4.dp)
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Total Aktivitas: \n ${defaultUser.TaskTotal}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color(0xFF2794EC),
                    )

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize(0.8f)
                            .align(Alignment.Center)
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        val arcSize = minOf(canvasWidth, canvasHeight) * 0.93f

//                      slices
                        data class Slice(
                            val value: Float,
                            val color: Color,
                            val label: String?
                        )

//                      Magic Slices
                        val slices = listOf(

                            Slice(defaultUser.TaskCompleted.toFloat(), Color(0xFF64B5F6),"Completed"),
                            Slice(defaultUser.TaskLate.toFloat(), Color(0xFFF06292),"Late"),
                            Slice(defaultUser.TaskPending.toFloat(), Color(0xFF81C784),"OnGoing"),
                        )


//                      magic chant for slice
                        val total = slices.sumOf { it.value.toDouble() }.toFloat()
                        var currentAngle = 0f

                        val topLeft = Offset(
                            (canvasWidth - arcSize) / 2f,
                            (canvasHeight - arcSize) / 2f
                        )


                        slices.forEach { slice ->
                            val sweep = 360f * (slice.value / total)
                            val strokeWidth = arcSize * 0.2f
                            val labelRadius = (arcSize / 1.4f)
                            drawArc(
                                color = slice.color,
                                startAngle = currentAngle,
                                sweepAngle = sweep,
                                useCenter = false,
                                topLeft = topLeft,
                                size = Size(arcSize, arcSize),
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                            )

//                      Magic Rune for each slice
                            slice.label?.let { text ->

                                val labelAngleDeg = currentAngle + sweep / 2f
                                val labelAngleRad = Math.toRadians(labelAngleDeg.toDouble())

                                val labelX = center.x + labelRadius * cos(labelAngleRad).toFloat()
                                val labelY = center.y + labelRadius * sin(labelAngleRad).toFloat()

                                drawContext.canvas.nativeCanvas.apply {
                                    drawText(
                                        text,
                                        labelX,
                                        labelY,
                                        android.graphics.Paint().apply {
                                            color = slice.color.toArgb()
                                            textSize = 42f
                                            textAlign = android.graphics.Paint.Align.CENTER
                                            isAntiAlias = true
                                            typeface = android.graphics.Typeface.DEFAULT_BOLD
                                        }
                                    )
                                }
                            }

//                          continuous slices for better AoE
                            currentAngle += sweep
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    val completedTasks = defaultUser.TaskCompleted
                    val lateTasks = defaultUser.TaskLate
                    val totalCompleted = completedTasks + lateTasks
                    val onTimePercentage = if (totalCompleted > 0) {
                        (completedTasks.toFloat() / totalCompleted.toFloat() * 100).toInt()
                    } else {
                        0
                    }

                    val TotalBelajar = defaultUser.BelajarTotal
                    val TotalTugas = defaultUser.TugasTotal
                    val TotalKerja = defaultUser.KerjaTotal


                    StatCard(
                        label = "Persentase Tepat Waktu",
                        value = "$onTimePercentage%",
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Belajar",
                        value = TotalBelajar.toString(),
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Kerja",
                        value = TotalKerja.toString(),
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Tugas",
                        value = TotalTugas.toString(),
                        color = Color(0xFF2260FF)
                    )
                }
            }
        }
    }
}


