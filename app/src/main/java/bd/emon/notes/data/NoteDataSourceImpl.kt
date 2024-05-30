package bd.emon.notes.data

import bd.emon.notes.domain.entity.Note

class NoteDataSourceImpl(private val db: NoteDatabase) : NoteDataSource {

    private val dao = db.noteDao()
    override suspend fun createNote(title: String, content: String) {
        dao.insertNote(Note(id = 0, title = title, content = content))
    }

    override suspend fun editNote(title: String, content: String) {
        dao.updateNote(Note(id = 0, title = title, content = content))
    }

    override suspend fun getNoteById(id: Int): Note {
        return dao.getNoteById(id)
    }

    override suspend fun getNotes(): List<Note> {
        return dao.getNotes()
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(id = note.id)
    }
}