package bd.emon.notes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import bd.emon.notes.domain.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
}