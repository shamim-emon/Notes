package bd.emon.notes.presentation.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    onSearchPressed: () -> Unit,
    onSettingPressed: () -> Unit,
    onAddNotePressed: () -> Unit,
    onNotePressed: (Int) -> Unit
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val notes by viewModel.notes.observeAsState(emptyList())
    var isScreenLoaded by remember { mutableStateOf(false) }

    if (!isScreenLoaded) {
        viewModel.getNotes()
        isScreenLoaded = true
    }

    HomeScreen(
        onSearchPressed = onSearchPressed,
        onSettingPressed = onSettingPressed,
        onAddNotePressed = onAddNotePressed,
        onNotePressed = onNotePressed,
        notes = notes
    )
}