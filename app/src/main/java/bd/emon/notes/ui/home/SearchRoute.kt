package bd.emon.notes.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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