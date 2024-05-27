package bd.emon.notes.domain.usecase

import bd.emon.notes.common.Response
import bd.emon.notes.data.NoteDBRepository

class GetNotesUseCase(
    val repository: NoteDBRepository
) {
    suspend fun getNotes() : Response {
        return repository.getNotes()
    }
}