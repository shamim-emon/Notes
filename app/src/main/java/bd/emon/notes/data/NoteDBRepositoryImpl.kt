package bd.emon.notes.data

import bd.emon.notes.domain.entity.Note

open class NoteDBRepositoryImpl(private val noteDataSource: NoteDataSource) : NoteDBRepository {
    override suspend fun createNote(title: String, content: String) {
        noteDataSource.createNote(title = title, content = content)
    }

    override suspend fun editNote(title: String, content: String) {
        noteDataSource.editNote(title = title, content = content)
    }

    override suspend fun getNoteById(id: Int): Note {
        return noteDataSource.getNoteById(id)
    }

    override suspend fun getNotes(): List<Note> {
        return noteDataSource.getNotes()
    }

    override suspend fun deleteNote(note: Note) {
        noteDataSource.deleteNote(note = note)
    }
}