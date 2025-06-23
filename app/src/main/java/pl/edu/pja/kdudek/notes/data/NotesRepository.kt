package pl.edu.pja.kdudek.notes.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.edu.pja.kdudek.notes.domain.model.ExposedNote

class NotesRepository(
    private val notesService: NotesService
) {
    suspend fun all(): List<ExposedNote> = withContext(Dispatchers.IO) {
        notesService.getAll()
    }

    suspend fun get(id: Int): ExposedNote = withContext(Dispatchers.IO) {
        notesService.getNote(id)
    }

    suspend fun create(note: ExposedNote) = withContext(Dispatchers.IO) {
        notesService.createNote(note)
    }

    suspend fun delete(id: Int) = withContext(Dispatchers.IO) {
        notesService.deleteNote(id)
    }
}
