package pl.edu.pja.kdudek.notes.ui.model

import androidx.compose.runtime.Stable
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote

sealed interface NotesListState {
    data object Loading : NotesListState

    @Stable
    data class Data(
        val notes: List<ExposedNote>
    ) : NotesListState
}