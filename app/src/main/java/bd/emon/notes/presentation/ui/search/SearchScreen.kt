package bd.emon.notes.presentation.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.presentation.ui.home.ContextBackground
import bd.emon.notes.presentation.ui.home.NoteList
import bd.emon.notes.presentation.ui.home.WaitView
import bd.emon.notes.presentation.ui.theme.NotesTheme
import bd.emon.notes.presentation.ui.theme.disabledAlpha
import bd.emon.notes.presentation.ui.theme.stronglyDeemphasizedAlpha

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSearch: (String) -> Unit,
    onNotePressed: (Int) -> Unit,
    notes: List<Note>,
    loadState: Boolean = false,
    searchBarText: String
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                SearchBar(
                    modifier = modifier,
                    searchBarText = searchBarText,
                    onBackPressed,
                    onSearch
                )
            },
            content = { innerPadding ->
                if (loadState) {
                    WaitView(innerPadding = innerPadding)
                }
                if (notes.isEmpty() && !loadState) {
                    if (searchBarText.length > 3) {
                        ContextBackground(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            backgroundImgId = R.drawable.bg_search_empty,
                            backgroundTextId = R.string.empty_search_result
                        )
                    } else {
                        ContextBackground(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            backgroundImgId = R.drawable.bg_search_initiate,
                            backgroundTextId = R.string.search_initiation
                        )
                    }
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
            }
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchBarText: String,
    onBackPressed: () -> Unit,
    onSearch: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
            .clip(RoundedCornerShape(32.dp))
            .border(
                BorderStroke(
                    1.5.dp,
                    SolidColor(MaterialTheme.colorScheme.inverseOnSurface)
                ),
                RoundedCornerShape(32.dp)
            )

    ) {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = searchBarText,
                onValueChange = {
                    onSearch.invoke(it)
                },
                leadingIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if (searchBarText.isNotEmpty()) {
                        IconButton(onClick = { onSearch.invoke("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = disabledAlpha),
                    focusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = disabledAlpha),
                    cursorColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = disabledAlpha),
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = disabledAlpha),
                ),
                placeholder = {
                    Row(
                        modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 4.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(
                                stronglyDeemphasizedAlpha
                            )
                        )
                        Text(
                            stringResource(R.string.search_hint),
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                stronglyDeemphasizedAlpha
                            ),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
                textStyle = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(
    name = "SearchScreen dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun SearchScreenPreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SearchScreen(
                onBackPressed = {},
                onSearch = {},
                onNotePressed = {},
                notes = emptyList(),
                loadState = false,
                searchBarText = ""
            )
        }
    }
}