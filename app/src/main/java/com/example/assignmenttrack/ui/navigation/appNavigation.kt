package com.example.assignmenttrack.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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

    var isNavigating by remember { mutableStateOf(false) }

    val safeNavigate: (String) -> Unit = { route ->
        if (!isNavigating) {
            isNavigating = true
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
            }
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect {
            isNavigating = false
        }
    }

    NavHost(
        navController = navController,
        // Global exit animation for all routes, slides to the left
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(1000)
            ) + fadeOut(animationSpec = tween(500))
        },
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            MainDashboard(
                onAddTaskClick = { safeNavigate(Screen.AddTask.route) },
                onProfileClick = { safeNavigate(Screen.Profile.route) },
                onStatClick = { safeNavigate(Screen.Stat.route) },
                onCalendarClick = { safeNavigate(Screen.Calendar.route) },
                onEditClick = { task -> safeNavigate(Screen.EditTask.createRoute(task.id)) },
                taskListViewModel = taskListViewModel
            )
        }

        // AddTask: Slide vertical from bottom
        composable(
            route = Screen.AddTask.route,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(1000),
                    initialOffsetY = { it } // Start from bottom
                ) + fadeIn(animationSpec = tween(1000))
            }
        ) {
            AddTaskScreen(
                onTaskSubmit = { safeNavigate(Screen.Dashboard.route) },
                taskListViewModel = taskListViewModel
            )
        }

        // Profile: Slide horizontal from left
        composable(
            route = Screen.Profile.route,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1000),
                    initialOffsetX = { -it } // Start from left
                ) + fadeIn(animationSpec = tween(1000))
            }
        ) {
            ProfileSection(onBackClick = { navController.popBackStack() })
        }

        // Stat: Slide horizontal from right
        composable(
            route = Screen.Stat.route,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1000),
                    initialOffsetX = { it } // Start from right
                ) + fadeIn(animationSpec = tween(1000))
            }
        ) {
            StatScreen(onBackClick = { navController.popBackStack() })
        }

        // Calendar: Slide horizontal from right
        composable(
            route = Screen.Calendar.route,
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(1000),
                    initialOffsetX = { it } // Start from right
                ) + fadeIn(animationSpec = tween(1000))
            }
        ) {
            CalendarRoute(onBackClick = { navController.popBackStack() })
        }

        // EditTask: Slide vertical from bottom
        composable(
            route = Screen.EditTask.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType }),
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(1000),
                    initialOffsetY = { it } // Start from bottom
                ) + fadeIn(animationSpec = tween(1000))
            }
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val tasks by taskListViewModel.tasks.collectAsState(initial = emptyList())
            val taskToEdit = tasks.find { it.id.toString() == taskId }

            if (taskToEdit != null) {
                EditTaskScreen(
                    onEditSubmit = { safeNavigate(Screen.Dashboard.route) },
                    taskListViewModel = taskListViewModel,
                    oldTask = taskToEdit
                )
            }
        }
    }
}