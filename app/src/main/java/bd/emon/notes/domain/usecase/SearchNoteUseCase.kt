package bd.emon.notes.domain.usecase

import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note

class SearchNoteUseCase(
    private val repository: NoteDBRepository
) {
    lateinit var keyword: String
    val isKeywordInitialised: Boolean
        get() = this::keyword.isInitialized

    suspend fun searchNote(keyword: String): List<Note> {
        this.keyword = keyword
        return repository.searchNote(keyword = this.keyword)
    }
}