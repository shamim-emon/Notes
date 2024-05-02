package bd.emon.notes.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.ui.theme.NotesTheme
import bd.emon.notes.ui.theme.stronglyDeemphasizedAlpha

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
    onSearchPressed: () -> Unit,
    onSettingPressed: () -> Unit,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                DefaultAppBar(
                    title = stringResource(id = R.string.app_name),
                    firstAction = onSearchPressed,
                    firstActionIcon = Icons.Filled.Search,
                    secondAction = onSettingPressed,
                    secondActionIcon = Icons.Filled.Settings
                )
            },
            content = content,
            floatingActionButton = {
                SmallFloatingActionButton(
                    onClick = { },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(Icons.Filled.Add, null)
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
@Preview
@Composable
private fun HomeScreenPreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(
                content = {
                    ContextBackground(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .fillMaxSize(),
                        backgroundImgId = R.drawable.bg_no_note,
                        backgroundTextId = R.string.first_note
                    )
                },
                onSearchPressed = {},
                onSettingPressed = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title: String,
    firstActionIcon: ImageVector,
    firstAction: () -> Unit,
    secondActionIcon: ImageVector,
    secondAction: () -> Unit,
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
                onClick = firstAction,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    firstActionIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha)
                )
            }

            IconButton(
                onClick = secondAction,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    secondActionIcon,
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
            contentDescription = null
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

@Preview(
    name = "NoNote light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "NoNote dark theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun NoNotePreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContextBackground(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                backgroundImgId = R.drawable.bg_no_note,
                backgroundTextId = R.string.first_note
            )
        }
    }
}

@Preview(
    name = "EmptySearch light theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "EmptySearch dark theme",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun EmptySearchPreview() {
    NotesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ContextBackground(
                modifier = Modifier
                    .padding(horizontal = 22.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                backgroundImgId = R.drawable.bg_search_empty,
                backgroundTextId = R.string.empty_search_result
            )
        }
    }
}