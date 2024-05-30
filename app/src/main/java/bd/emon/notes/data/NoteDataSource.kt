package bd.emon.notes.data

import bd.emon.notes.domain.entity.Note

interface NoteDataSource {
    suspend fun createNote(title: String, content: String)
    suspend fun editNote(title: String, content: String)
    suspend fun getNoteById(id: Int): Note
    suspend fun getNotes(): List<Note>
    suspend fun deleteNote(note: Note)
}