package bd.emon.notes.presentation.ui.search

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
fun SearchRoute(
    onBackPressed: () -> Unit,
    onNotePressed: (Int) -> Unit
) {
    val context = LocalContext.current
    val viewModel: SearchViewModel = hiltViewModel()
    val loadState by viewModel.loadState.observeAsState(initial = true)
    val errorState by viewModel.errorState.observeAsState()
    val searchBarTextState by viewModel.searchBarText.observeAsState("")
    val notes by viewModel.notes.observeAsState(emptyList())
    // This flag is used to reload search result when coming back from note details screen
    var comingBackFromNoteDetails by remember { mutableStateOf(false) }

    val onSearch: (String) -> Unit = {
        viewModel.searchNote(it)
        viewModel.setSearchBarText(it)
    }

    if (comingBackFromNoteDetails) {
        viewModel.searchNote(searchBarTextState)
        comingBackFromNoteDetails = false
    }

    LaunchedEffect(key1 = errorState) {
        errorState?.localizedMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    SearchScreen(
        onBackPressed = onBackPressed,
        onSearch = onSearch,
        onNotePressed = {
            onNotePressed.invoke(it)
            comingBackFromNoteDetails = true
        },
        notes = notes,
        loadState = loadState,
        searchBarText = searchBarTextState
    )
}