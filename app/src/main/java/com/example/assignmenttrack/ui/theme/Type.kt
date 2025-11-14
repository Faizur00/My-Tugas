package com.example.assignmenttrack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.assignmenttrack.R

val leagueSpartan = FontFamily(
    Font(R.font.leaguespartan_bold, FontWeight.Bold),
    Font(R.font.leaguespartan_regular, FontWeight.Normal),
    Font(R.font.leaguespartan_medium, FontWeight.Medium),
    Font(R.font.leaguespartan_semibold, FontWeight.SemiBold),
    Font(R.font.leaguespartan_light, FontWeight.Light),
    Font(R.font.leaguespartan_thin, FontWeight.Thin),
    Font(R.font.leaguespartan_extrabold, FontWeight.ExtraBold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = leagueSpartan,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = leagueSpartan,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = leagueSpartan,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
