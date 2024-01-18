package com.example.notes.presentation.navigation

sealed class Screens(val route: String) {
    object ListScreen : Screens(route = "list_screen")
    object PrioritySettingsScreen : Screens(route = "priority_settings_screen")
    object DetailsScreen : Screens(route = "details_screen?noteId={noteId}") {
        fun routeWithId(noteId: Int): String = "details_screen?noteId=${noteId}"
    }
}
