package bd.emon.notes.domain.usecase

import bd.emon.notes.common.Response
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note

class GetNotesUseCase(
    val repository: NoteDBRepository
) {
    suspend fun getNotes() : List<Note> {
        return repository.getNotes()
    }
}