package pl.edu.pja.kdudek.notes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pl.edu.pja.kdudek.notes.App
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote
import pl.edu.pja.kdudek.notes.ui.model.NoteAddState

class NoteAddViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = (application as App).notesRepository

    val state = MutableStateFlow<NoteAddState>(NoteAddState.Data)

    fun resetState() {
        state.update {
            NoteAddState.Data
        }
    }

    suspend fun onAdd(title: String, content: String) {
        state.update {
            NoteAddState.Loading
        }
        repository.create(
            ExposedNote(
                id = -1,
                title = title,
                content = content
            )
        )
    }
}