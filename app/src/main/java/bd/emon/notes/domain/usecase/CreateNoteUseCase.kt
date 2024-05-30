package bd.emon.notes.domain.usecase

import bd.emon.notes.data.NoteDBRepository

class CreateNoteUseCase(
    private val repository: NoteDBRepository
) {

    lateinit var title: String
    lateinit var content: String
    suspend fun createNote(title: String, content: String) {
        this.title = title
        this.content = content
        return repository.createNote(title = title, content = content)
    }
}