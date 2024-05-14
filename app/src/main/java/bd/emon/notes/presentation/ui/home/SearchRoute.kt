package bd.emon.notes.presentation.ui.home

import androidx.compose.runtime.Composable

@Composable
fun SearchRoute(
    onBackPressed: () -> Unit,
    onSearch: (String) -> Unit
) {

    SearchScreen(
        onBackPressed = onBackPressed,
        onSearch = onSearch
    )
}