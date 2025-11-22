package com.example.assignmenttrack.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignmenttrack.uiStateData.defaultUser
import com.example.assignmenttrack.ui.screen.MainDashboard
import com.example.assignmenttrack.ui.screen.AddTaskScreen
import com.example.assignmenttrack.ui.screen.CalendarRoute
import com.example.assignmenttrack.ui.screen.ProfileSection
import com.example.assignmenttrack.ui.screen.StatScreen
import com.example.assignmenttrack.viewModel.TaskListViewModel

// handle the type safety navigation between different screen.
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object AddTask : Screen("add_task")
    data object Profile : Screen("profile")
    data object Stat : Screen("stat")

    data object Calendar : Screen("Calendar")
}


// host the navigation graph and define the different screen.
@Composable
fun AppNavigation(navController: NavHostController, viewModel: TaskListViewModel) {
    val taskListViewModel: TaskListViewModel = hiltViewModel()
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
                onStatClick = { navController.navigate(Screen.Stat.route) },
                onCalendarClick = { navController.navigate(Screen.Calendar.route) },
                taskListViewModel = taskListViewModel
            )
        }

        composable(Screen.AddTask.route){
            AddTaskScreen(
                onTaskSubmit = { navController.navigate(Screen.Dashboard.route) },
                taskListViewModel = taskListViewModel
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

        composable (Screen.Calendar.route){
            CalendarRoute(
                /*TODO : Create Pop Back*/
            )
        }
    }
}