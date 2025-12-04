package com.example.assignmenttrack.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.estimateAnimationDurationMillis
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    var isLauncherActive by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        isLauncherActive = false
        uri?.let {
            userViewModel.updatePhotoProfile(context, it)
        }
    } // Library ambil foto dari galerr, outputnya Uri

    val changeNameDialogState = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
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
                    .clickable {
                        if (!isLauncherActive) {
                            isLauncherActive = true
                            launcher.launch("image/*")
                        }
                    },
                painter = rememberAsyncImagePainter(
                    model = user.profilePicturePath?.takeIf { it.isNotEmpty() }?.let { File(it) }
                        ?: R.drawable.profile
                ),
                contentScale = ContentScale.Crop,
                contentDescription = ("User Profile"),
            )

            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
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
                text = "\"Victory is reserved for those who are willing to pay its price.\"\n\n-Sun Tzu",
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

//    Change Name Dialog Handler
        if (changeNameDialogState.value) {
            ChangeNameDialog(
                onDismiss = { changeNameDialogState.value = false },
                userViewModel = userViewModel
            )
        }
    }
}
