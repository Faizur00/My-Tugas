package com.example.assignmenttrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.assignmenttrack.ui.navigation.AppNavigation
import com.example.assignmenttrack.ui.theme.AssignmentTrackTheme
import com.example.assignmenttrack.viewModel.TaskListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTrackTheme {
                val viewModel: TaskListViewModel = hiltViewModel()
                AppNavigation(rememberNavController() , viewModel)
            }
        }
    }
}
