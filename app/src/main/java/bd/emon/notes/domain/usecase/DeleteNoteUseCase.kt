package bd.emon.notes.domain.usecase

import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note

class DeleteNoteUseCase(
    private val repository: NoteDBRepository
) {
    lateinit var note :Note
    suspend fun deleteNote(note: Note){
        this.note = note
        repository.deleteNote(note = note)
    }
}