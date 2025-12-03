package com.example.assignmenttrack.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.assignmenttrack.ui.screen.MainDashboard
import com.example.assignmenttrack.ui.screen.AddTaskScreen
import com.example.assignmenttrack.ui.screen.CalendarRoute
import com.example.assignmenttrack.ui.screen.EditTaskScreen
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

    data object EditTask : Screen("edit_task/{taskId}"){
        fun createRoute(taskId: Int) = "edit_task/$taskId"
    }
}


// host the navigation graph and define the different screen.
@Composable
fun AppNavigation(navController: NavHostController) {
    val taskListViewModel: TaskListViewModel = hiltViewModel()

    // Navigation throttling state - prevents multiple rapid clicks
    var isNavigating by remember { mutableStateOf(false) }

    // Safe navigation wrapper that blocks duplicate navigation attempts
    val safeNavigate: (String) -> Unit = { route ->
        if (!isNavigating) {
            isNavigating = true
            navController.navigate(route) {
                launchSingleTop = true // Prevent duplicate destinations
            }
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            isNavigating = false // Re-enable navigation
        }
    }

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
        composable(Screen.Dashboard.route){
            MainDashboard(
                // Replace all direct navController.navigate() calls
                onAddTaskClick = { safeNavigate(Screen.AddTask.route) },
                onProfileClick = { safeNavigate(Screen.Profile.route) },
                onStatClick = { safeNavigate(Screen.Stat.route) },
                onCalendarClick = { safeNavigate(Screen.Calendar.route) },
                onEditClick = { task -> safeNavigate(Screen.EditTask.createRoute(task.id)) },
                taskListViewModel = taskListViewModel
            )
        }

        composable(Screen.AddTask.route){
            AddTaskScreen(
                onTaskSubmit = { safeNavigate(Screen.Dashboard.route) },
                taskListViewModel = taskListViewModel
            )
        }

        // popBackStack() calls don't need throttling (less prone to duplicates)
        composable(Screen.Profile.route){
            ProfileSection(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Stat.route){
            StatScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Calendar.route){
            CalendarRoute(onBackClick = { navController.popBackStack() })
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType})
        ){ backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val tasks by taskListViewModel.tasks.collectAsState(initial = emptyList())
            val taskToEdit = tasks.find { it.id.toString() == taskId }

            if (taskToEdit != null){
                EditTaskScreen(
                    onEditSubmit = { safeNavigate(Screen.Dashboard.route) },
                    taskListViewModel = taskListViewModel,
                    oldTask = taskToEdit
                )
            }
        }
    }
}