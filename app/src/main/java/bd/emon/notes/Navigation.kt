package bd.emon.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bd.emon.notes.Destination.HOME_ROUTE
import bd.emon.notes.Destination.NOTE_ROUTE
import bd.emon.notes.Destination.SEARCH_ROUTE
import bd.emon.notes.ui.home.HomeRoute
import bd.emon.notes.ui.home.SearchRoute
import bd.emon.notes.ui.note.NoteDetailsRoute

object Destination {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
    const val NOTE_ROUTE = "note"
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
                onSettingPressed = {},
                onAddNotePressed = {
                    navController.navigate(route = NOTE_ROUTE)
                }
            )
        }

        composable(route = SEARCH_ROUTE) {
            SearchRoute(
                onSearch = {},
                onBackPressed = { navController.navigateUp() }
            )
        }
        composable(route = NOTE_ROUTE) {
            NoteDetailsRoute(
                onBackPressed = { navController.navigateUp() }
            )
        }
    }
}