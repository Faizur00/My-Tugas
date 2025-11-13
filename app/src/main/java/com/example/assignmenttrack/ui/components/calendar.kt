package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Calendar(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.Blue)
            .fillMaxWidth(0.9f)
            .padding(64.dp),
        contentAlignment = Alignment.Center

    ){
        Text(text = "CALENDAR(BLM JADI, GK TAU GMN NJINK CARA BUATNYA)", color = Color.White)
    }
}