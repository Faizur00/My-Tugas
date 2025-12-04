package com.example.assignmenttrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.disableHotReloadMode
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.assignmenttrack.ui.components.ChangeNameDialog
import com.example.assignmenttrack.ui.navigation.AppNavigation
import com.example.assignmenttrack.ui.screen.StatScreen
import com.example.assignmenttrack.ui.theme.AssignmentTrackTheme
import com.example.assignmenttrack.viewModel.TaskListViewModel
import com.example.assignmenttrack.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint // Module dipakai mulai bisa dipakai mulai dari sini
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AssignmentTrackTheme {
                val snackbarHostState = remember {
                    SnackbarHostState()
                }


                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { }

                AppNavigation(rememberNavController())
            }
        }
    }
}

