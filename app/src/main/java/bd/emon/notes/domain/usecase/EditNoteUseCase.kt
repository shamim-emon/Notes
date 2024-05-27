package bd.emon.notes.domain.usecase

import bd.emon.notes.common.Response
import bd.emon.notes.data.NoteDBRepository

class EditNoteUseCase(
    private val repository: NoteDBRepository
) {
    lateinit var title: String
    lateinit var content: String

    suspend fun editNote(title: String, content: String): Response {
        this.title = title
        this.content = content
        return repository.editNote(title = title, content = content)
    }
}