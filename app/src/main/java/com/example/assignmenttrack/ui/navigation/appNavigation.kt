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
import com.example.assignmenttrack.Model.User
import com.example.assignmenttrack.Model.defaultUser
import com.example.assignmenttrack.ui.screen.MainDashboard
import com.example.assignmenttrack.ui.screen.AddTaskScreen
import com.example.assignmenttrack.ui.screen.ProfileSection
import com.example.assignmenttrack.ui.screen.StatScreen

// handle the type safety navigation between different screen.
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object AddTask : Screen("add_task")
    data object Profile : Screen("profile")
    data object Stat : Screen("stat")

}


// host the navigation graph and define the different screen.
@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        enterTransition = {
            slideInVertically( animationSpec = tween(1000),
                initialOffsetY = { it }
            ) + fadeIn(animationSpec = tween(1000))
        },
        exitTransition = { fadeOut() },
        startDestination = Screen.Dashboard.route
    ) {
        composable( Screen.Dashboard.route){
            MainDashboard(
                onAddTaskClick = { navController.navigate(Screen.AddTask.route) },
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onStatClick = { navController.navigate(Screen.Stat.route) }
            )
        }

        composable(Screen.AddTask.route){
            AddTaskScreen(
                /*TODO: create a pop back*/
            )
        }

        composable(Screen.Profile.route){
            ProfileSection(
                nama = defaultUser.name,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Stat.route){
            StatScreen(
                /*TODO: Create Pop BAck*/
            )
        }
    }
}