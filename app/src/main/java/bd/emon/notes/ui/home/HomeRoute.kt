package bd.emon.notes.ui.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onSearchPressed: () -> Unit,
    onSettingPressed: () -> Unit,
    onAddNotePressed: () -> Unit,
) {
    HomeScreen(
        onSearchPressed = onSearchPressed,
        onSettingPressed = onSettingPressed,
        onAddNotePressed = onAddNotePressed
    )
}