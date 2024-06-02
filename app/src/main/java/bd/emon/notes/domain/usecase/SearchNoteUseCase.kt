package bd.emon.notes.domain.usecase

import bd.emon.notes.data.NoteDBRepository

class SearchNoteUseCase(
    private val repository: NoteDBRepository
) {
    fun searchNote(keyword: String) {}
}