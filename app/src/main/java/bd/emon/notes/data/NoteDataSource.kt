package bd.emon.notes.data

interface NoteDataSource {
    suspend fun createNote(title: String, content: String)
    suspend fun editNote(title: String, content: String)
}