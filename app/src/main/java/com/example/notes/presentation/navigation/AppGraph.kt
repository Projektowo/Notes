package com.example.notes.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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

    // If you are in a Compose environment
    val applicationContext = LocalContext.current
    val sharedPref = applicationContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val widgetParameter = sharedPref.getString("widgetParameter", null)

    if (widgetParameter != null) {
        val editor = sharedPref.edit()
        editor.remove("widgetParameter")
        editor.apply()
        DetailsScreen(navController = navController,)
    }
}
