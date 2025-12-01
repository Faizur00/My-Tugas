package com.example.assignmenttrack.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.assignmenttrack.ui.components.StatCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.viewModel.UserViewModel
import kotlin.math.abs

@Composable
fun StatScreen(viewModel: UserViewModel = hiltViewModel(), onBackClick: () -> Unit){

    val stat by viewModel.stat.collectAsStateWithLifecycle()

    val totalTask = stat.taskTotal
    val completedTasks = stat.taskCompleted
    val lateTasks = stat.taskLate


    val totalBelajar = stat.belajarTotal
    val totalTugas = stat.tugasTotal
    val totalKerja = stat.kerjaTotal


    val onTimePercentage = if (completedTasks + lateTasks > 0)
    {
        (completedTasks.toFloat() / (completedTasks + lateTasks).toFloat() * 100).toInt()
    }
    else
    {
        0
    }

    val animationStarted = remember { mutableStateOf(false) }
    val animatedPercentage by animateFloatAsState(
        targetValue = if (animationStarted.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1200)
    )


    val animatedOnTimeStat by animateFloatAsState(
        targetValue = if (animationStarted.value) onTimePercentage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1200)
    )

    val animatedTotalStudy by animateFloatAsState(
        targetValue = if (animationStarted.value) totalBelajar.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1200)
    )

    val animatedTotalWork by animateFloatAsState(
        targetValue = if (animationStarted.value) totalKerja.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1200)
    )

    val animatedTotalAssignment by animateFloatAsState(
        targetValue = if (animationStarted.value) totalTugas.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1200)
    )

    // Trigger the animation when the composable is first drawn
    androidx.compose.runtime.LaunchedEffect(Unit) {
        animationStarted.value = true
    }





    Surface(color = Color(0xFFCAD6FF)){
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            IconButton(
                onClick = {onBackClick()},
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding( top = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Balik",
                    tint = Color(0xFF2260FF)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "My Statistik",
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
                        text = "Aktivitas: \n ${stat.taskTotal}",
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
                            val value:  Float,
                            val color: Color,
                            val label: String?
                        )

//                      Magic Slices with animation
                        val slices = listOf(
                            Slice(completedTasks.toFloat(), Color(0xFF81C784),"Completed"),
                            Slice(lateTasks.toFloat(), Color(0xFFF06292),"Late"),
                            Slice(totalTask - completedTasks - lateTasks.toFloat(), Color(0xFF6489F6),"OnGoing"),
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
                            drawArc(
                                color = slice.color,
                                startAngle = currentAngle,
                                sweepAngle = sweep * animatedPercentage,
                                useCenter = false,
                                topLeft = topLeft,
                                size = Size(arcSize, arcSize),
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                            )

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
                    StatCard(
                        label = "Persentase Tepat Waktu:",
                        value = "${(animatedOnTimeStat).toInt()}%",
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Belajar",
                        value = "${(animatedTotalStudy).toInt()}",
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Kerja",
                        value = "${(animatedTotalWork).toInt()}",
                        color = Color(0xFF2260FF)
                    )
                    StatCard(
                        label = "Total Tugas",
                        value = "${(animatedTotalAssignment).toInt()}",
                        color = Color(0xFF2260FF)
                    )
                }
            }
        }
    }
}


