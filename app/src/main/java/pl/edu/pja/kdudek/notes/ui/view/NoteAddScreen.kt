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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pl.edu.pja.kdudek.notes.ui.model.NoteAddState
import pl.edu.pja.kdudek.notes.ui.theme.NotesTheme
import pl.edu.pja.kdudek.notes.ui.theme.Typography
import pl.edu.pja.kdudek.notes.ui.viewmodel.NoteAddViewModel

@Composable
fun NoteAddScreen(
    addViewModel: NoteAddViewModel,
    controller: NavHostController
) {
    val state by addViewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        addViewModel.resetState()
    }

    when (state) {
        NoteAddState.Data -> {
            NoteAdd(
                onAddClick = { title, content ->
                    scope.launch {
                        addViewModel.onAdd(title, content)
                        controller.popBackStack()
                    }
                }
            )
        }

        NoteAddState.Loading -> {
            LoadingView()
        }
    }
}

@Composable
fun NoteAdd(
    onAddClick: (String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                value = title,
                onValueChange = { title = it },
                textStyle = Typography.titleLarge
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            HorizontalDivider(
                thickness = 2.dp
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                value = content,
                onValueChange = { content = it },
                textStyle = Typography.bodyLarge
            )
            Row {
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        onAddClick(title, content)
                    },
                ) {
                    Text(
                        text = "Add"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
private fun NoteAddPreview() {
    NotesTheme {
        NoteAdd(
            onAddClick = { _, _, -> /* no-op */ }
        )
    }
}
