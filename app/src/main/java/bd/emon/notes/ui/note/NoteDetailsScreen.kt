package bd.emon.notes.ui.note

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.ui.theme.NotesTheme
import bd.emon.notes.ui.theme.stronglyDeemphasizedAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteDetailsScreen(
    modifier: Modifier = Modifier,
    title: String,
    noteTitle: String,
    noteContent: String,
    readOnly: Boolean,
    onBackPressed: () -> Unit,
    onSavePressed: () -> Unit,
    onEditPressed: () -> Unit
) {


    var notTileState by remember {
        mutableStateOf(noteTitle)
    }

    var notContentState by remember {
        mutableStateOf(noteContent)
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                NoteDetailsAppBar(
                    modifier = Modifier,
                    title = title,
                    readOnly = readOnly,
                    onBackPressed = onBackPressed,
                    onEditPressed = onEditPressed,
                    onSavePressed = onSavePressed
                )
            },
            content = { contentPadding ->
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    TextField(
                        readOnly = readOnly,
                        value = notTileState,
                        onValueChange = { notTileState = it },
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
                        value = notContentState,
                        onValueChange = { notContentState = it },
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
                noteTitle = "Dummy note with Multiple line support to check its limit",
                noteContent = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                readOnly = true,
                onBackPressed = {},
                onSavePressed = {},
                onEditPressed = {}
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
                        onClick = onSavePressed,
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            Icons.Default.Save,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(
                                stronglyDeemphasizedAlpha
                            )
                        )
                    }
                }
            }

        }
    )
}