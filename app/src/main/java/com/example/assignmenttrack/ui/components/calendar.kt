package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Calendar(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(0.9f),
        color = Color.Blue
    ) {
        Box(
            modifier = Modifier
                .padding(64.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "CALENDAR(BLM JADI, GK TAU GMN NJINK CARA BUATNYA)",
                color = Color.White
            )
        }
    }
}