package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.R

// Profil (bagian atas di dashboard)
@Composable
fun ProfileSection(name: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // bagian buat show profile pict
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.98f)
                        .padding(all = 8.dp)
                        .clip(shape = CircleShape)
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape),
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = ("User Profile"),
                )

                Text(
                    text = "Halo, $name",
                    color = Color.Black, style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            // setting button/notif (kgk tau buat apaan pakek aja dlu)
            IconButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Surface(
                    modifier = Modifier.size(42.dp),
                    shape = CircleShape,
                    color = Color(0xFF2260FF)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White
                    )
                }
            }
        }
    }
}