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

@Composable
fun HomeRoute(
    onSearchPressed: () -> Unit,
    onAddNotePressed: () -> Unit,
    onNotePressed: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = hiltViewModel()
    val notes by viewModel.notes.observeAsState(emptyList())
    val loadState by viewModel.loadState.observeAsState(initial = true)
    val errorState by viewModel.errorState.observeAsState()
    var isScreenLoaded by remember { mutableStateOf(false) }

    if (!isScreenLoaded) {
        viewModel.getNotes()
        isScreenLoaded = true
    }

    LaunchedEffect(key1 = errorState) {
        errorState?.localizedMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    HomeScreen(
        onSearchPressed = onSearchPressed,
        onAddNotePressed = {
            onAddNotePressed.invoke()
            isScreenLoaded = false
        },
        onNotePressed = {
            onNotePressed.invoke(it)
            isScreenLoaded = false
        },
        notes = notes,
        loadState = loadState
    )
}