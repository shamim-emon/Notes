package bd.emon.notes.presentation.ui.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import bd.emon.notes.R
import bd.emon.notes.common.NO_ID

@Composable
fun NoteDetailsRoute(
    noteId: Int,
    onBackPressed: () -> Unit
) {
    val viewModel: NoteDetailsViewModel = hiltViewModel()
    var readOnlyState by remember {
        mutableStateOf(noteId != NO_ID)
    }

    val note by viewModel.getNoteById.observeAsState()
    val loadState by viewModel.loadState.observeAsState()
    val errorState by viewModel.errorState.observeAsState()

    var noteIdState by rememberSaveable {
        mutableIntStateOf(noteId)
    }

    LaunchedEffect(key1 = noteIdState) {
        if (noteIdState != NO_ID) {
            viewModel.getNoteById(noteIdState)
        }
    }

    NoteDetailsScreen(
        title = stringResource(id = R.string.app_name),
        noteTitle = note?.title ?: "",
        noteContent = note?.content ?: "",
        readOnly = readOnlyState,
        onBackPressed = onBackPressed,
        onSavePressed = {
            readOnlyState = true
        },
        onEditPressed = { readOnlyState = false }
    )
}