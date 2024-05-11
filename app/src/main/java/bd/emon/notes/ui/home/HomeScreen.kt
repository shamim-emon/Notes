package bd.emon.notes.ui.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import bd.emon.notes.R
import bd.emon.notes.ui.theme.NotesTheme
import bd.emon.notes.ui.theme.stronglyDeemphasizedAlpha

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchPressed: () -> Unit,
    onSettingPressed: () -> Unit,
    onAddNotePressed: () -> Unit
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                HomeAppBar(
                    title = stringResource(id = R.string.app_name),
                    onSearchPressed = onSearchPressed,
                    onSettingPressed = onSettingPressed
                )
            },
            content = { innerPadding ->
                ContextBackground(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    backgroundImgId = R.drawable.bg_no_note,
                    backgroundTextId = R.string.first_note
                )
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
                onSettingPressed = {},
                onAddNotePressed = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onSearchPressed: () -> Unit,
    onSettingPressed: () -> Unit,

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

            IconButton(
                onClick = onSettingPressed,
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    Icons.Outlined.Settings,
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