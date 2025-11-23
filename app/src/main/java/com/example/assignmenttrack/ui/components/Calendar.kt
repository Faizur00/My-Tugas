package com.example.assignmenttrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmenttrack.uiStateData.CalendarTask
import com.example.assignmenttrack.ui.utils.CalendarUtils

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarTask>,
    year:Int,
    month:Int,
    onDayClick:(Int, Int, Int)->Unit,
    onMonthChange:(Int, Int) -> Unit = {_, _ ->}
    ) {

    val monthData = CalendarUtils.calculateMonthData(year, month)

    Column{
        CalendarHeader(
            year = year,
            month = month,
            onPreviousClick = {
                val (newMonth, newYear) = CalendarUtils.getPreviousMonth(month, year)
                onMonthChange(newMonth, newYear)
            },
            onNextClick = {
                val (newMonth, newYear) = CalendarUtils.getNextMonth(month, year)
                onMonthChange(newMonth, newYear)
            }
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            CalendarDayHeader()

            LazyVerticalGrid( // dibagi jadi 7 kolom
                modifier = Modifier
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(CalendarUtils.CALENDAR_COLUMNS),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(monthData.offset) { index -> // kotak-kotak bulan lalu
                    val day = monthData.previousMonthStartDay + index
                    val (prevMonth, prevYear) = CalendarUtils.getPreviousMonth(month, year)
                    CalendarDayCells(
                        day = day,
                        isCurrentMonth = false,
                        hasTask = false,
                        isToday = false,
                        onClick = { onDayClick(day, prevMonth, prevYear) }
                    )
                }

                items(monthData.daysInMonth) { index -> // kotak-kotak bulan ini
                    val day = index + 1
                    val tasks = calendarInput.find { it.day == day }
                    val hasTask = tasks?.tasks?.any { task ->
                        when (task.status) {
                            true -> false
                            false, null -> true
                        }
                    } ?: false
                    val isToday = CalendarUtils.isToday(day, month, year)
                    CalendarDayCells(
                        day = day,
                        isCurrentMonth = true,
                        hasTask = hasTask,
                        isToday = isToday,
                        onClick = { onDayClick(day, month, year) }
                    )
                }

                items(monthData.remainingCells) { index -> // kotak-kotak bulan depan
                    val day = index + 1
                    val (nextMonth, nextYear) = CalendarUtils.getNextMonth(month, year)

                    CalendarDayCells(
                        day = day,
                        isCurrentMonth = false,
                        hasTask = false,
                        isToday = false,
                        onClick = { onDayClick(day, nextMonth, nextYear) }
                    )
                }
            }
        }
    }
}

// tempat bulan sama tahun kalender
@Composable
private fun CalendarHeader(
    year: Int,
    month: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, start = 16.dp, end = 16.dp)
            .height(35.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(Color(0xFF2260FF))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousClick){ // tombol kiri
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    tint = Color.White,
                    contentDescription = "Previous Month"
                )
            }

            Text(
                modifier = Modifier
                    .padding(top= 5.dp),
                text = "${CalendarUtils.monthNames[month - 1]} $year",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = Color.White,
            )

            IconButton(onClick = onNextClick){ // tombol kanan
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = "Next Month"
                )
            }
        }
    }
}

// nama2 hari di kalender
@Composable
private fun CalendarDayHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        CalendarUtils.dayNames.forEach { day ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2260FF))
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}


// kotak-kotak di kalender
@Composable
private fun CalendarDayCells(
    day: Int,
    isCurrentMonth: Boolean,
    isToday: Boolean,
    hasTask: Boolean,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ){
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when {
                        hasTask -> Color(0xFF2260FF)
                        isToday -> Color.White
                        else -> Color.Transparent
                    },
                )
                .border(
                    color = when {
                        isToday -> Color(0xFF2260FF)
                        else -> Color.Transparent
                    },
                    width = 1.25.dp,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = day.toString(),
                color = when {
                    !isCurrentMonth -> Color.LightGray
                    hasTask -> Color.White
                    isToday -> Color(0xFF2260FF)
                    else -> Color.Black
                },
                fontWeight = when {
                    isToday || hasTask -> FontWeight.Bold
                    else -> FontWeight.Normal
                },
                fontSize = when {
                    !isCurrentMonth -> 10.sp
                    else -> 12.sp
                },
                textAlign = TextAlign.Center
            )
        }
    }
}