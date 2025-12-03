package com.example.assignmenttrack.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.R
import com.example.assignmenttrack.ui.components.ChangeNameDialog
import com.example.assignmenttrack.viewModel.UserViewModel
import java.io.File

@Composable
fun ProfileSection(userViewModel: UserViewModel = hiltViewModel(), onBackClick: () -> Unit){
    val user by userViewModel.user.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { userViewModel.updatePhotoProfile(context, uri) }
    } // Library ambil foto dari galerr, outputnya Uri

    val changeNameDialogState = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
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
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
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
                    .clickable { launcher.launch("image/*") },
                painter =  rememberAsyncImagePainter(
                    model = user.profilePicturePath?.takeIf { it.isNotEmpty() }?.let { File(it) }
                        ?: R.drawable.profile
                ),
                contentScale = ContentScale.Crop,
                contentDescription = ("User Profile"),
            )

            Spacer(Modifier.height(8.dp))

            Row (modifier = Modifier.align(Alignment.CenterHorizontally)) {
                IconButton(
                    onClick = { changeNameDialogState.value = true },
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
                    text = user.name,
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


//    Change Name Dialog Handler
    if (changeNameDialogState.value){
        ChangeNameDialog(onDismiss = { changeNameDialogState.value = false }, userViewModel = userViewModel)
    }
}
