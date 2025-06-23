package pl.edu.pja.kdudek.notes.ui.model

import androidx.compose.runtime.Stable
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote

sealed interface NoteDetailsState {
    data object Loading : NoteDetailsState

    @Stable
    data class Data(
        val note: ExposedNote
    ) : NoteDetailsState
}