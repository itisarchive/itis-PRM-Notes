package pl.edu.pja.kdudek.notes.ui.model

sealed interface NoteAddState {
    data object Loading : NoteAddState
    data object Data : NoteAddState
}