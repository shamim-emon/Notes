package bd.emon.notes.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import bd.emon.notes.common.NO_ID
import bd.emon.notes.presentation.ui.Destination.HOME_ROUTE
import bd.emon.notes.presentation.ui.Destination.NOTE_ROUTE
import bd.emon.notes.presentation.ui.Destination.SEARCH_ROUTE
import bd.emon.notes.presentation.ui.home.HomeRoute
import bd.emon.notes.presentation.ui.note.NoteDetailsRoute
import bd.emon.notes.presentation.ui.search.SearchRoute

object Destination {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val NOTE_ROUTE = "note?id="
}

@Composable
fun NotesNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
    ) {
        composable(route = HOME_ROUTE) {
            HomeRoute(
                onSearchPressed = { navController.navigate(route = SEARCH_ROUTE) },
                onAddNotePressed = {
                    navController.navigate(route = "$NOTE_ROUTE{id}")
                },
                onNotePressed = { noteId ->
                    navController.navigate(route = "$NOTE_ROUTE$noteId")
                }
            )
        }

        composable(route = SEARCH_ROUTE) {
            SearchRoute(
                onSearch = {},
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(
            route = "note?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = NO_ID
                }
            )
        ) {
            val noteId = it.arguments!!.getInt("id")
            NoteDetailsRoute(
                noteId = noteId,
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}