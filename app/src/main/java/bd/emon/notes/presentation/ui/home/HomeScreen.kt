package bd.emon.notes.presentation.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.presentation.ui.theme.NotesTheme
import bd.emon.notes.presentation.ui.theme.stronglyDeemphasizedAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchPressed: () -> Unit,
    onAddNotePressed: () -> Unit,
    onNotePressed: (Int) -> Unit,
    notes: List<Note>,
    loadState: Boolean = false
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                HomeAppBar(
                    title = stringResource(id = R.string.app_name),
                    onSearchPressed = onSearchPressed
                )
            },
            content = { innerPadding ->
                if (loadState) {
                    WaitView(innerPadding = innerPadding)
                }
                if (notes.isEmpty() && !loadState) {
                    ContextBackground(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        backgroundImgId = R.drawable.bg_no_note,
                        backgroundTextId = R.string.first_note
                    )
                }
                if (notes.isNotEmpty() && !loadState) {
                    NoteList(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        notes = notes,
                        onNotePressed = onNotePressed
                    )
                }
            },
            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = onAddNotePressed,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Outlined.Add, null)
                }
            }
        )
    }
}

@Preview(
    name = "HomeScreen light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "HomeScreen dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun HomeScreenPreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                onSearchPressed = {},
                onAddNotePressed = {},
                onNotePressed = {},
                notes = emptyList()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onSearchPressed: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium
            )
        },
        modifier = modifier,
        actions = {
            IconButton(
                onClick = onSearchPressed,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha)
                )
            }
        }
    )
}

@Composable
fun ContextBackground(
    modifier: Modifier = Modifier,
    backgroundImgId: Int,
    backgroundTextId: Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = backgroundImgId),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(id = backgroundTextId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()

        )
    }
}

@Composable
fun Thumbnail(
    modifier: Modifier = Modifier,
    note: Note,
    onThumbNailPressed: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = { onThumbNailPressed.invoke(note.id) })

    ) {
        Text(
            text = note.title,
            modifier = Modifier
                .padding(27.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(
    name = "NoteListPreview light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "NoteListPreview dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun NoteListPreview() {

    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NoteList(
                    notes = listOf(
                        Note(
                            id = 1,
                            title = "Book Review : The Design of Everyday Things by Don Norman",
                            content = "This is content of note 1"
                        ),
                        Note(id = 2, title = "Note2", content = "This is content of note 2"),
                        Note(id = 3, title = "Note3", content = "This is content of note 3"),
                        Note(id = 4, title = "Note4", content = "This is content of note 4")
                    ),
                    onNotePressed = {}
                )
            }
        }
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onNotePressed: (Int) -> Unit
) {

    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            notes.forEach {
                item {
                    Thumbnail(
                        note = it,
                        onThumbNailPressed = onNotePressed
                    )
                }
            }
        }
    }
}

@Composable
fun WaitView(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}