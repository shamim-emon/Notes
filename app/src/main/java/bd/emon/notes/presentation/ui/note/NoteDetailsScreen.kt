package bd.emon.notes.presentation.ui.note

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.common.NO_ID
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.presentation.ui.home.WaitView
import bd.emon.notes.presentation.ui.theme.NotesTheme
import bd.emon.notes.presentation.ui.theme.disabledAlpha
import bd.emon.notes.presentation.ui.theme.stronglyDeemphasizedAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteDetailsScreen(
    modifier: Modifier = Modifier,
    title: String,
    note: Note,
    readOnly: Boolean,
    loading: Boolean,
    onBackPressed: () -> Unit,
    onSavePressed: (Int, String, String) -> Unit,
    onEditPressed: () -> Unit,
    onModifyNote: (note: Note) -> Unit
) {
    var showDiscardChangesDialog by remember {
        mutableStateOf(false)
    }

    val discardChangesIfReadOnly = {
        if (readOnly) {
            onBackPressed.invoke()
        } else {
            showDiscardChangesDialog = true
        }
    }
    var saveButtonEnabled by remember {
        mutableStateOf(note.title.isNotEmpty() && note.content.isNotEmpty())
    }

    saveButtonEnabled = note.title.isNotEmpty() && note.content.isNotEmpty()

    var showSaveNoteDialog by remember {
        mutableStateOf(false)
    }

    if (showSaveNoteDialog) {
        NoteAlertDialog(
            onDismissRequest = { showSaveNoteDialog = false },
            onConfirmation = {
                onSavePressed.invoke(note.id, note.title, note.content)
                showSaveNoteDialog = false
            },
            dialogTitle = stringResource(id = R.string.dialog_save_note_title)
        )
    }

    if (showDiscardChangesDialog) {
        NoteAlertDialog(
            onDismissRequest = { showDiscardChangesDialog = false },
            onConfirmation = {
                showDiscardChangesDialog = false
                onBackPressed.invoke()
            },
            dialogTitle = stringResource(id = R.string.dialog_discard_note_title)
        )
    }

    BackHandler(enabled = true, onBack = {
        discardChangesIfReadOnly.invoke()
    })

    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                NoteDetailsAppBar(
                    modifier = Modifier,
                    title = title,
                    readOnly = readOnly,
                    saveButtonEnabled = saveButtonEnabled,
                    onBackPressed = { discardChangesIfReadOnly.invoke() },
                    onEditPressed = onEditPressed,
                    onSavePressed = { showSaveNoteDialog = true }
                )
            },
            content = { contentPadding ->
                if (loading) {
                    WaitView(innerPadding = contentPadding)
                } else {
                    Box(
                        modifier = Modifier
                            .padding(contentPadding)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {

                        Column {
                            TextField(
                                readOnly = readOnly,
                                value = note.title,
                                onValueChange = {
                                    onModifyNote.invoke(note.copy(title = it))
                                },
                                modifier = Modifier
                                    .padding(all = 16.dp)
                                    .wrapContentHeight(),
                                textStyle = MaterialTheme.typography.displaySmall,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                    focusedContainerColor = MaterialTheme.colorScheme.background,
                                    cursorColor = MaterialTheme.colorScheme.onBackground,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                                ),
                                placeholder = {
                                    Text(
                                        stringResource(R.string.note_title_hint),
                                        color = MaterialTheme.colorScheme.onSurface.copy(
                                            stronglyDeemphasizedAlpha
                                        ),
                                        style = MaterialTheme.typography.displaySmall
                                    )
                                }
                            )

                            TextField(
                                readOnly = readOnly,
                                value = note.content,
                                onValueChange = {
                                    onModifyNote.invoke(note.copy(content = it))
                                },
                                modifier = Modifier
                                    .padding(
                                        top = 37.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )
                                    .wrapContentHeight()
                                    .fillMaxWidth(),
                                textStyle = MaterialTheme.typography.titleLarge,
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                    focusedContainerColor = MaterialTheme.colorScheme.background,
                                    cursorColor = MaterialTheme.colorScheme.onBackground,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.background,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                                ),
                                placeholder = {
                                    Text(
                                        stringResource(R.string.note_content_hint),
                                        color = MaterialTheme.colorScheme.onSurface.copy(
                                            stronglyDeemphasizedAlpha
                                        ),
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                }

                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview(
    name = "NoteScreenViewMode light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "NoteScreenViewMode dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun NoteScreenViewModePreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoteDetailsScreen(
                title = "My Note",
                note = Note(
                    id = NO_ID,
                    title = "Dummy note with Multiple line support to check its limit",
                    content = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
                ),
                readOnly = true,
                loading = false,
                onBackPressed = {},
                onSavePressed = { _, _, _ -> },
                onEditPressed = {},
                onModifyNote = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailsAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    readOnly: Boolean,
    saveButtonEnabled: Boolean,
    onEditPressed: () -> Unit,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit

) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha)
                )
            }
        },
        modifier = modifier,
        actions = {
            when (readOnly) {
                true -> {
                    IconButton(
                        onClick = onEditPressed,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(
                                stronglyDeemphasizedAlpha
                            )
                        )
                    }
                }

                else -> {
                    IconButton(
                        onClick = {
                            onSavePressed.invoke()
                        },
                        modifier = Modifier.padding(4.dp),
                        enabled = saveButtonEnabled
                    ) {
                        Icon(
                            Icons.Outlined.Save,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(
                                if (saveButtonEnabled) stronglyDeemphasizedAlpha else disabledAlpha
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun NoteAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    icon: ImageVector = Icons.Outlined.Info,
) {
    AlertDialog(
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        icon = {
            Icon(icon, contentDescription = null)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_confirm_button_title),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_dismiss_button_title),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}

@Preview(
    name = "AlertDialog light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "AlertDialog dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)

@Composable
private fun AlertDialogPreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoteAlertDialog(
                onDismissRequest = {},
                onConfirmation = {},
                dialogTitle = "Save Changes?"
            )
        }
    }
}