package bd.emon.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import bd.emon.notes.common.getOrAwaitValue
import bd.emon.notes.data.NoteDao
import bd.emon.notes.data.NoteDatabase
import bd.emon.notes.domain.entity.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var noteDatabase: NoteDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        noteDatabase = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = NoteDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        noteDao = noteDatabase.noteDao()
    }

    @Test
    fun insert_note_success_note_created() = runBlocking {
        val note = Note(
            title = "Note Title",
            content = "Note Content"
        )

        noteDao.insertNote(note)
        val result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result.size == 1).isTrue()
        assertThat(result[0].title == note.title).isTrue()
        assertThat(result[0].content == note.content).isTrue()
    }

    @Test
    fun delete_single_note_success_note_deleted() = runBlocking {
        val note = Note(
            title = "Note Title",
            content = "Note Content"
        )
        noteDao.insertNote(note)
        var result = noteDao.getNotes().getOrAwaitValue()
        noteDao.deleteNote(result[0].id)
        result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result.size == 1).isFalse()
    }

    @Test
    fun delete_all_note_success_all_notes_deleted() = runBlocking {
        val noteOne = Note(
            title = "Note1 Title",
            content = "Note1 Content"
        )

        val noteTwo = Note(
            title = "Note2 Title",
            content = "Note2 Content"
        )
        noteDao.insertNote(noteOne)
        noteDao.insertNote(noteTwo)
        var result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result.size == 2).isTrue()

        noteDao.deleteAllNote()
        result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result.isEmpty()).isTrue()
    }

    @Test
    fun update_single_note_success_note_updated() = runBlocking {
        var note = Note(
            title = "Note1 Title",
            content = "Note1 Content"
        )

        noteDao.insertNote(note)

        var result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result[0].title == "Note1 Title").isTrue()
        assertThat(result[0].content == "Note1 Content").isTrue()

        note = result[0]
        note.title = "New Title"
        note.content = "New Content"

        noteDao.updateNote(note)
        result = noteDao.getNotes().getOrAwaitValue()
        assertThat(result.size == 1).isTrue()
        assertThat(result[0].title == "New Title").isTrue()
        assertThat(result[0].content == "New Content").isTrue()
    }
    @Test
    fun fetch_single_note_by_id_success_correct_note_fetched() = runBlocking {
        var note = Note(
            title = "Note1 Title",
            content = "Note1 Content"
        )

        noteDao.insertNote(note)

        var result = noteDao.getNotes().getOrAwaitValue()
        val id = result[0].id

        note = noteDao.getNoteById(id).getOrAwaitValue()
        assertThat(note.id == id).isTrue()
    }

    @Test
    fun fetch_single_note_by_keyword_in_any_field_success_correct_note_fetched() = runBlocking {
        val note = Note(
            title = "Note1 Title",
            content = "Note Content"
        )
        noteDao.insertNote(note)

        // search keyword in title
        var results = noteDao.getNotesByKeyword("Note1 Tit").getOrAwaitValue()

        results.forEach { note ->
            assertThat(note.title.contains("Note1 Tit")).isTrue()
        }

        // search keyword in content
        results = noteDao.getNotesByKeyword("Cont").getOrAwaitValue()

        results.forEach { note ->
            assertThat(note.content.contains("Cont")).isTrue()
        }
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }
}