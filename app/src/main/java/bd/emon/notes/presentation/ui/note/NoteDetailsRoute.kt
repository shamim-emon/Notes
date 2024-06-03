package bd.emon.notes.presentation.ui.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NoteDetailsRoute(
    onBackPressed: () -> Unit
) {
    val noteDetailsViewModel: NoteDetailsViewModel = hiltViewModel()
    var readOnlyState by remember {
        mutableStateOf(false)
    }
    NoteDetailsScreen(
        title = "",
        noteTitle = "",
        noteContent = "",
        readOnly = readOnlyState,
        onBackPressed = onBackPressed,
        onSavePressed = { readOnlyState = true },
        onEditPressed = { readOnlyState = false }
    )
}