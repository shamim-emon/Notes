package bd.emon.notes

import bd.emon.notes.common.DELETE_ERROR
import bd.emon.notes.common.FETCH_ERROR
import bd.emon.notes.common.any
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.data.NoteDBRepositoryImpl
import bd.emon.notes.data.NoteDataSource
import bd.emon.notes.domain.entity.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryUnitTest {

    lateinit var repository: NoteDBRepository

    @Mock
    lateinit var dataSource: NoteDataSource

    var notes: List<Note> = listOf(
        Note(id = 1, title = "Note1", content = "This is content of note 1"),
        Note(id = 2, title = "Note2", content = "This is content of note 2"),
        Note(id = 3, title = "Note3", content = "This is content of note 3"),
        Note(id = 4, title = "Note4", content = "This is content of note 4"),
        Note(id = 5, title = "Note5", content = "This is content of note 5"),
        Note(id = 6, title = "Note6", content = "This is content of note 6"),
        Note(id = 7, title = "Note7", content = "This is content of note 7"),
    )

    val note = Note(id = 1, title = "Note1", content = "This is content of note 1")

    @Before
    fun setUp() {
        repository = NoteDBRepositoryImpl(dataSource)
    }

    //region getNotes() tests
    @Test
    fun `getNotes on success return notes`() = runTest {
        getNotesSuccess()
        val _notes = repository.getNotes()
        assertThat(_notes == notes).isTrue()
    }

    @Test
    fun `getNotes on error throw exception`() = runTest {
        try {
            getNotesError()
            repository.getNotes()
        } catch (e: Exception) {
            assertThat(e).hasMessageThat().contains(FETCH_ERROR)
        }
    }

    //endregion
    //region deleteNote
    @Test
    fun `deleteNote on success return unit`() = runTest {
        deleteNoteSuccess()
        val response = repository.deleteNote(note = note)
        assertThat(response == Unit).isTrue()
    }

    @Test
    fun `deleteNote on error throw exception`() = runTest {
        try {
            deleteNoteError()
            repository.deleteNote(note = note)
        } catch (e: Exception) {
            assertThat(e).hasMessageThat().contains(DELETE_ERROR)
        }
    }
    //endregion

    //region helper functions
    private suspend fun getNotesSuccess() {
        `when`(
            dataSource.getNotes()
        ).thenReturn(notes)
    }

    private suspend fun getNotesError() {
        `when`(
            dataSource.getNotes()
        ).thenThrow(RuntimeException(FETCH_ERROR))
    }

    private suspend fun deleteNoteSuccess() {
        `when`(
            dataSource.deleteNote(any(Note::class.java))
        ).thenReturn(Unit)
    }

    private suspend fun deleteNoteError() {
        `when`(
            dataSource.deleteNote(any(Note::class.java))
        ).thenThrow(RuntimeException(DELETE_ERROR))
    }
    //endregion
}