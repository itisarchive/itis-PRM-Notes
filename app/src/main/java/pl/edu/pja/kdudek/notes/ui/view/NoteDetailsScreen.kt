package pl.edu.pja.kdudek.notes.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote
import pl.edu.pja.kdudek.notes.ui.model.NoteDetailsState
import pl.edu.pja.kdudek.notes.ui.theme.NotesTheme
import pl.edu.pja.kdudek.notes.ui.theme.Typography
import pl.edu.pja.kdudek.notes.ui.viewmodel.NoteDetailsViewModel

@Composable
fun NoteDetailsScreen(
    detailsViewModel: NoteDetailsViewModel,
    controller: NavHostController,
    id: Int
) {
    val state by detailsViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (id != -1) {
            detailsViewModel.loadData(id)
        } else {
            controller.popBackStack()
        }
    }

    when (val state = state) {
        is NoteDetailsState.Data -> {
            NoteDetails(
                note = state.note,
            ) {
                scope.launch {
                    detailsViewModel.onDelete()
                    controller.popBackStack()
                }
            }
        }
        NoteDetailsState.Loading -> {
            LoadingView()
        }
    }
}

@Composable
fun NoteDetails(
    note: ExposedNote,
    onDeleteClick: (ExposedNote) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = note.title,
                style = Typography.titleLarge
            )
            HorizontalDivider(
                thickness = 2.dp
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = note.content,
                style = Typography.bodyLarge
            )
            Row {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { onDeleteClick(note) },
                ) {
                    Text(
                        text = "Remove"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
private fun NoteDetailsPreview() {
    NotesTheme {
        NoteDetails(
            note = ExposedNote(
                id = 1,
                title = "Sample Note",
                content = """
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
                    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                """.trimIndent()
            ),
            onDeleteClick = { /* no-op */ }
        )
    }
}
