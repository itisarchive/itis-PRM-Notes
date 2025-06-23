package pl.edu.pja.kdudek.notes.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote
import pl.edu.pja.kdudek.notes.ui.model.NotesListState
import pl.edu.pja.kdudek.notes.ui.theme.NotesTheme
import pl.edu.pja.kdudek.notes.ui.viewmodel.NotesListViewModel

@Composable
fun NotesListScreen(
    listViewModel: NotesListViewModel,
    controller: NavHostController
) {
    val state by listViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        listViewModel.loadData()
    }

    when (val state = state) {
        is NotesListState.Data -> {
            NotesList(
                notes = state.notes,
                onItemClick = {
                    controller.navigate("/notes/${it.id}")
                }
            )
        }
        NotesListState.Loading -> {
            LoadingView()
        }
    }
}

@Composable
fun NotesList(
    notes: List<ExposedNote>,
    onItemClick: (ExposedNote) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(notes) {
            Row(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                NoteItem(it, onItemClick)
            }
        }
    }
}

@Composable
fun NoteItem(
    note: ExposedNote,
    onItemClick: (ExposedNote) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onItemClick(note) }
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = note.title
            )
        }
    }
}

@Preview
@Composable
private fun NoteItemPreview() {
    NotesTheme {
        NoteItem(
            note = ExposedNote(
                id = 1,
                title = "Sample Note",
                content = "This is a sample note content."
            ),
            onItemClick = { /* no-op */ }
        )
    }
}

@Preview
@Composable
private fun NotesListPreview() {
    NotesTheme {
        NotesList(
            notes = List(10) {
                ExposedNote(
                    id = it,
                    title = "Note $it",
                    content = "This is the content of note number $it."
                )
            },
            onItemClick = { /* no-op */ }
        )
    }
}
