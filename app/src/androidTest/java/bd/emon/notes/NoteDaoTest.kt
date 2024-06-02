package bd.emon.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
        val result = noteDao.getNotes()
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
        var result = noteDao.getNotes()
        noteDao.deleteNote(result[0].id)
        result = noteDao.getNotes()
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
        var result = noteDao.getNotes()
        assertThat(result.size == 2).isTrue()

        noteDao.deleteAllNote()
        result = noteDao.getNotes()
        assertThat(result.isEmpty()).isTrue()
    }

    @Test
    fun update_single_note_success_note_updated() = runBlocking {
        var note = Note(
            title = "Note1 Title",
            content = "Note1 Content"
        )

        noteDao.insertNote(note)

        var result = noteDao.getNotes()
        assertThat(result[0].title == "Note1 Title").isTrue()
        assertThat(result[0].content == "Note1 Content").isTrue()

        note = result[0]
        note.title = "New Title"
        note.content = "New Content"

        noteDao.updateNote(note)
        result = noteDao.getNotes()
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

        var result = noteDao.getNotes()
        val id = result[0].id

        note = noteDao.getNoteById(id)
        assertThat(note.id == id).isTrue()
    }

    @Test
    fun fetch_single_note_by_keyword_in_title() = runBlocking {
        val note = Note(
            title = "My dynamic notebook",
            content = "Some Dynamic content1."
        )
        noteDao.insertNote(note)
        val noteTwo = Note(
            title = "My dynamic cool notebook",
            content = "Some Dynamic cool content2."
        )
        noteDao.insertNote(noteTwo)
        val noteRandom = Note(
            title = "My random cool notebook",
            content = "Some  cool content."
        )
        noteDao.insertNote(noteRandom)

        var results = noteDao.getNotesByKeyword("nam")
        assertThat(results.size).isEqualTo(2)

        assertThat(results[0].title.contains("nam")).isTrue()
        assertThat(results[1].title.contains("nam")).isTrue()
    }

    @Test
    fun fetch_single_note_by_keyword_in_content() = runBlocking {
        val note = Note(
            title = "My Journal 22",
            content = "I liked death note manga a lot.... "
        )
        noteDao.insertNote(note)
        val noteTwo = Note(
            title = "My Journal 23",
            content = "Hunter x Hunter was another great manga..."
        )
        noteDao.insertNote(noteTwo)
        val noteRandom = Note(
            title = "My Journal 24",
            content = "These days I don't get to follow any tv shows because of time..."
        )
        noteDao.insertNote(noteRandom)

        var results = noteDao.getNotesByKeyword("Manga")
        assertThat(results.size).isEqualTo(2)

        assertThat(results[0].content.contains("manga")).isTrue()
        assertThat(results[1].content.contains("manga")).isTrue()
    }

    @After
    fun tearDown() {
        noteDatabase.close()
    }
}