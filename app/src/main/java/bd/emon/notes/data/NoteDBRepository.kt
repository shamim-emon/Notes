package bd.emon.notes.data

import bd.emon.notes.common.Response
import bd.emon.notes.domain.entity.Note

interface NoteDBRepository {
    suspend fun createNote(title: String, content: String): Response
    suspend fun editNote(title: String, content: String): Response

    suspend fun getNoteById(id: Int): Response
    suspend fun getNotes(): List<Note>
}