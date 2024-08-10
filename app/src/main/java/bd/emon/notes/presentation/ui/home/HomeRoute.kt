package bd.emon.notes.presentation.ui.home

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import bd.emon.notes.domain.entity.Note

@Composable
fun HomeRoute(
    onSearchPressed: () -> Unit,
    onAddNotePressed: () -> Unit,
    onNotePressed: (Int) -> Unit,
    reloadPage: Boolean
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val notes by viewModel.notes.observeAsState(emptyList())
    val loadState by viewModel.loadState.observeAsState(initial = true)
    val errorState by viewModel.errorState.observeAsState()
    var isScreenLoaded by remember { mutableStateOf(false) }
    var reloadPageState by remember { mutableStateOf(reloadPage) }

    if (reloadPageState) {
        viewModel.getNotes()
        reloadPageState = false
    }
    LaunchedEffect(key1 = isScreenLoaded) {
        if (!isScreenLoaded) {
            viewModel.getNotes()
            isScreenLoaded = true
        }
    }

    val onDeleteNotePressed: (Note) -> Unit = { note: Note ->
        viewModel.deleteNote(note)
        viewModel.onNoteDeleted(note)
    }

    LaunchedEffect(key1 = errorState) {
        errorState?.localizedMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        onSearchPressed = onSearchPressed,
        onAddNotePressed = onAddNotePressed,
        onNotePressed = onNotePressed,
        onDeleteNotePressed = onDeleteNotePressed,
        notes = notes,
        loadState = loadState
    )
}