package com.example.notes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.notes.presentation.details.DetailsScreen
import com.example.notes.presentation.list.ListScreen
import com.example.notes.presentation.prioritysettings.PrioritySettingsScreen

@Composable
fun AppGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.ListScreen.route
    ) {
        composable(route = Screens.ListScreen.route) {
            ListScreen(navController = navController)
        }
        composable(route = Screens.PrioritySettingsScreen.route) {
            PrioritySettingsScreen(navController = navController)
        }
        composable(
            route = Screens.DetailsScreen.route,
            arguments = listOf(
                navArgument("noteId") {
                    defaultValue = null
                    type = NavType.StringType // compose does not support nullable int
                    nullable = true
                }
            )
        ) {
            DetailsScreen(navController = navController,)
        }
    }
}
