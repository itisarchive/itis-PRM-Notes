package pl.edu.pja.kdudek.notes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.edu.pja.kdudek.notes.App
import pl.edu.pja.kdudek.notes.ui.model.NoteDetailsState

class NoteDetailsViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository = (application as App).notesRepository

    val state = MutableStateFlow<NoteDetailsState>(NoteDetailsState.Loading)

    fun loadData(id: Int) {
        viewModelScope.launch {
            repository.get(id).let { note ->
                state.update { NoteDetailsState.Data(note) }
            }
        }
    }

    suspend fun onDelete() {
        val id = (state.value as? NoteDetailsState.Data)?.note?.id ?: return
        repository.delete(id)
    }
}