package com.example.assignmenttrack.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignmenttrack.data.User
import com.example.assignmenttrack.ui.screen.MainDashboard
import com.example.assignmenttrack.ui.screen.AddTaskScreen
import com.example.assignmenttrack.ui.screen.ProfileSection





// handle the type safety navigation between different screen.
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object AddTask : Screen("add_task")

    data object Profile : Screen("profile")
}


// host the navigation graph and define the different screen.
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        enterTransition = { slideInVertically( animationSpec = tween(1000), initialOffsetY = { it } ) + fadeIn() },
        exitTransition = { fadeOut() },
        startDestination = Screen.Dashboard.route
    ) {
        composable( Screen.Dashboard.route){
            MainDashboard(
                onAddTaskClick = { navController.navigate(Screen.AddTask.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }

        composable(Screen.AddTask.route){
            AddTaskScreen(
                /*TODO: create a pop back*/
            )
        }

        composable(Screen.Profile.route){
            ProfileSection(
                nama = User().name,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}