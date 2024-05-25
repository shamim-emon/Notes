package bd.emon.notes.data

import bd.emon.notes.domain.entity.Note

class NoteDataSourceImpl(private val db: NoteDatabase) : NoteDataSource {

    private val dao = db.noteDao()
    override suspend fun createNote(title: String, content: String) {
        dao.insertNote(Note(id = 0, title = title, content = content))
    }
}