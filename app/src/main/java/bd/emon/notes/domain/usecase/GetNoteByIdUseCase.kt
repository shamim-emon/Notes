package bd.emon.notes.domain.usecase

import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note

class GetNoteByIdUseCase(
    private val repository: NoteDBRepository
) {
    var id: Int = -1
    suspend fun getNoteById(id: Int): Note {
        this.id = id
        return repository.getNoteById(id = id)
    }
}