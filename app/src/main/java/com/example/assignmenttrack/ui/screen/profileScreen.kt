package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.Model.defaultUser
import com.example.assignmenttrack.ui.components.FormField1
import com.example.assignmenttrack.ui.theme.leagueSpartan

@Composable
fun ProfileSection(nama: String, onBackClick: () -> Unit){
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
        Alignment = TopCenter
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

//          Profile Header
            Column(
                /*TODO: Add Modifier*/
            ){
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Balik",
                            tint = Color(0xFF2260FF)
                        )
                    }
                    Text(
                        text = "My Profile",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = leagueSpartan,
                        color = Color(0xFF2260FF),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Image(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape)
                        .align(Alignment.CenterHorizontally)
                        .width(150.dp)
                        .height(150.dp)
                        .clickable { /*TODO: Feature to change profile picture*/},
                    painter = painterResource(id = defaultUser.ProfilePictureId),
                    contentDescription = ("User Profile"),
                )

                Text(
                    text = nama,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = leagueSpartan
                )
            }

//          Profile Form Field
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(24.dp)
            ){
                var nama by remember { mutableStateOf("") }
                var gktau1 by remember { mutableStateOf("") }
                var gktau2 by remember { mutableStateOf("") }

                FormField1(title = "Nama",
                    value = nama,
                    onValueChange = { nama = it },
                    titleFontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(16.dp))

                FormField1(
                    title = "Gk tau isi apaan",
                    value = gktau1,
                    onValueChange = { gktau1 = it },
                    titleFontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(16.dp))

                FormField1(
                    title = "Emg mau isi apaan jir? to do list doang",
                    value = gktau2,
                    onValueChange = { gktau2 = it },
                    titleFontWeight = FontWeight.Medium
                )
            }
        }
    }
}