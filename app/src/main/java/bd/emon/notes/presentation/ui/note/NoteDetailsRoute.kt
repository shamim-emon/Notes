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
import bd.emon.notes.domain.entity.Note

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

    val note by viewModel.getNoteById.observeAsState(initial = Note(NO_ID, "", ""))
    val loadState by viewModel.loadState.observeAsState(initial = false)
    val errorState by viewModel.errorState.observeAsState()
    var successMessage by remember {
        mutableStateOf("")
    }
    val createNoteSuccessMessage = stringResource(R.string.note_create_success)
    val updateNoteSuccessMessage = stringResource(R.string.note_update_success)

    var noteIdState by rememberSaveable {
        mutableIntStateOf(noteId)
    }

    val onSavePressed: (Int, String, String) -> Unit = { id, title, content ->
        if (noteIdState == NO_ID) {
            viewModel.createNote(title = title, content = content)
            successMessage = createNoteSuccessMessage
        } else {
            viewModel.editNote(id = id, title = title, content = content)
            successMessage = updateNoteSuccessMessage
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

    LaunchedEffect(key1 = errorState) {
        errorState?.localizedMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    NoteDetailsScreen(
        title = stringResource(id = R.string.app_name),
        note = note,
        readOnly = readOnlyState,
        loading = loadState,
        onBackPressed = onBackPressed,
        onSavePressed = onSavePressed,
        onEditPressed = onEditPressed,
        onModifyNote = { viewModel.modifyNote(it) }
    )
}