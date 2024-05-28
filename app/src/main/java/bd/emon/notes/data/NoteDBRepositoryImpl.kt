package bd.emon.notes.data

import android.util.Log
import bd.emon.notes.common.Response
import bd.emon.notes.domain.entity.Note

open class NoteDBRepositoryImpl(private val noteDataSource: NoteDataSource) : NoteDBRepository {
    override suspend fun createNote(title: String, content: String): Response {
        return try {
            noteDataSource.createNote(title = title, content = content)
            Response.Success<Note>(null)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage)
        }
    }

    override suspend fun editNote(title: String, content: String): Response {
        return try {
            noteDataSource.editNote(title = title, content = content)
            Response.Success<Note>(null)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage)
        }
    }

    override suspend fun getNoteById(id: Int): Response {
        return try {
            val note = noteDataSource.getNoteById(id)
            Response.Success(note)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage)
        }
    }

    override suspend fun getNotes(): List<Note> {
        return noteDataSource.getNotes()
    }
}