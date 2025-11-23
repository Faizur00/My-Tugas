package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import com.example.assignmenttrack.uiStateData.defaultUser
import com.example.assignmenttrack.ui.components.FormField1
import com.example.assignmenttrack.ui.theme.leagueSpartan

@Composable
fun ProfileSection(nama: String, onBackClick: () -> Unit){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        color = Color.White
    ){
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(top = 16.dp)
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
                    .width(200.dp)
                    .height(200.dp)
                    .clickable { /*TODO: Feature to change profile picture*/ },
                painter = painterResource(id = defaultUser.profilePictureId),
                contentDescription = ("User Profile"),
            )

            Spacer(Modifier.height(8.dp))

            Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .height(40.dp)
                        .width(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Black,
                    )
                }

                Text(
                    text = nama,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(top = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = leagueSpartan,
                )
            }

            Text(
                text = "ini gak tau mau ditulis apaan, cmn bagian bawahnya kosong jadi isi aja sembarang, ini deskripsi pancasila pakai bahasa rusia: \n \n Панчасила – это основа индонезийского государства, основанная на пяти принципах: вера в Единого Всемогущего Бога; справедливое и цивилизованное человечество; единство Индонезии; демократия, основанная на мудрости совещательного и представительного подхода; и социальная справедливость для всех индонезийцев. Панчасила, слово, происходящее от санскрита и означающее «пять» (panca) и «принцип» (sila), служит моральным ориентиром и руководством для всех индонезийцев в их национальной и государственной жизни.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(all =12.dp),
                fontWeight = FontWeight.Medium,
                fontFamily = leagueSpartan,
                textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
            )
        }
    }
}