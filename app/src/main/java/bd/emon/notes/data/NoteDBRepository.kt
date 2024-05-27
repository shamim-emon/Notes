package bd.emon.notes.data

import bd.emon.notes.common.Response

interface NoteDBRepository {
    suspend fun createNote(title: String, content: String): Response
    suspend fun editNote(title: String, content: String): Response

    suspend fun getNoteById(id: Int): Response
    suspend fun getNotes(): Response
}