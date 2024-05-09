package bd.emon.notes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import bd.emon.notes.Destination.HOME_ROUTE
import bd.emon.notes.Destination.SEARCH_ROUTE
import bd.emon.notes.ui.home.HomeRoute
import bd.emon.notes.ui.home.SearchRoute

object Destination {
    const val HOME_ROUTE = "home"
    const val SEARCH_ROUTE = "search"
}

@Composable
fun NotesNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
    ){
        composable(HOME_ROUTE){
            HomeRoute(
                onSearchPressed = {navController.navigate(SEARCH_ROUTE)},
                onSettingPressed = {  },
                onAddNotePressed = {}
            )
        }

        composable(SEARCH_ROUTE){
            SearchRoute(
                onSearch = {},
                onBackPressed = {navController.navigateUp()}
            )
        }
    }
}