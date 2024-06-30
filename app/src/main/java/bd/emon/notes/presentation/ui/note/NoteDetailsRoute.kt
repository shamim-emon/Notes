package bd.emon.notes.presentation.ui.note

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import bd.emon.notes.R
import bd.emon.notes.common.NO_ID

@Composable
fun NoteDetailsRoute(
    noteId: Int,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: NoteDetailsViewModel = hiltViewModel()
    var readOnlyState by remember {
        mutableStateOf(noteId != NO_ID)
    }

    val note by viewModel.getNoteById.observeAsState()
    val loadState by viewModel.loadState.observeAsState(false)
    val errorState by viewModel.errorState.observeAsState()
    var successMessage by remember {
        mutableStateOf("")
    }

    var noteIdState by rememberSaveable {
        mutableIntStateOf(noteId)
    }

    val onSavePressed: (Int, String, String) -> Unit = { id, title, content ->
        if (noteIdState == NO_ID) {
            viewModel.createNote(title = title, content = content)
            successMessage = "Successfully created note"
        } else {
            viewModel.editNote(id = id, title = title, content = content)
            successMessage = "Successfully updated note"
        }
        onBackPressed.invoke()
    }

    val onEditPressed: () -> Unit = {
        readOnlyState = false
    }

    LaunchedEffect(key1 = noteIdState) {
        if (noteIdState != NO_ID) {
            viewModel.getNoteById(noteIdState)
        }
    }

    LaunchedEffect(key1 = successMessage) {
        if (successMessage != "") {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        }
    }

    NoteDetailsScreen(
        title = stringResource(id = R.string.app_name),
        noteId = noteIdState,
        noteTitle = note?.title ?: "",
        noteContent = note?.content ?: "",
        readOnly = readOnlyState,
        loading = loadState,
        onBackPressed = onBackPressed,
        onSavePressed = onSavePressed,
        onEditPressed = onEditPressed
    )
}