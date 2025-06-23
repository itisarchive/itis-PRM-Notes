package pl.edu.pja.kdudek.notes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.edu.pja.kdudek.notes.App
import pl.edu.pja.kdudek.notes.ui.model.NotesListState

class NotesListViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = (application as App).notesRepository

    val state = MutableStateFlow<NotesListState>(NotesListState.Loading)

    fun loadData() {
        viewModelScope.launch {
            repository.all().let { notes ->
                state.update { NotesListState.Data(notes) }
            }
        }
    }
}