package bd.emon.notes.domain.usecase

import bd.emon.notes.common.NO_ID
import bd.emon.notes.data.NoteDBRepository

class EditNoteUseCase(
    private val repository: NoteDBRepository
) {
    var id: Int = NO_ID
    lateinit var title: String
    lateinit var content: String

    suspend fun editNote(id: Int, title: String, content: String) {
        this.id = id
        this.title = title
        this.content = content
        repository.editNote(id = id, title = title, content = content)
    }
}