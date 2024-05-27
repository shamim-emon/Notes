package bd.emon.notes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import bd.emon.notes.domain.entity.Note

@Dao
interface NoteDao {
    @Throws(Exception::class)
    @Insert
    suspend fun insertNote(note: Note)
    @Throws(Exception::class)
    @Query("SELECT * from Note")
    fun getNotes(): List<Note>
    @Throws(Exception::class)
    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id: Int)
    @Throws(Exception::class)
    @Query("DELETE  FROM Note")
    fun deleteAllNote()
    @Throws(Exception::class)
    @Update(entity = Note::class)
    fun updateNote(note: Note)
    @Throws(Exception::class)
    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: Int): Note
    @Throws(Exception::class)
    @Query("SELECT * FROM Note WHERE (title LIKE '%' || :keyword || '%') OR (content LIKE '%' || :keyword || '%')  ")
    fun getNotesByKeyword(keyword: String): List<Note>
}