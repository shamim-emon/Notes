package bd.emon.notes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import bd.emon.notes.domain.entity.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insertNote(note: Note)
    @Query("SELECT * from Note")
    fun getNotes(): LiveData<List<Note>>

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id: Int)
    @Query("DELETE  FROM Note")
    fun deleteAllNote()
    @Update(entity = Note::class)
    fun updateNote(note: Note)

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>
    @Query("SELECT * FROM Note WHERE (title LIKE '%' || :keyword || '%') OR (content LIKE '%' || :keyword || '%')  ")
    fun getNotesByKeyword(keyword: String): LiveData<List<Note>>
}