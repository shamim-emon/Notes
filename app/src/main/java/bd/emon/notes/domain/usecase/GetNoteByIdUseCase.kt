package bd.emon.notes.domain.usecase

import bd.emon.notes.common.Response
import bd.emon.notes.data.NoteDBRepository

class GetNoteByIdUseCase(
    private val repository: NoteDBRepository
) {
    var id: Int = -1
    suspend fun getNoteById(id: Int): Response {
        this.id = id
       return repository.getNoteById(id = id)
    }
}