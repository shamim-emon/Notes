package bd.emon.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.FETCH_ERROR
import bd.emon.notes.common.INSERT_ERROR
import bd.emon.notes.common.Response
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.UPDATE_ERROR
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.data.NoteDBRepositoryImpl
import bd.emon.notes.data.NoteDataSource
import bd.emon.notes.domain.entity.Note
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteDetailsRepositoryUnitTest {

    lateinit var repository: NoteDBRepository

    @Mock
    lateinit var dataSource: NoteDataSource

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    val NOTE_ID = 1
    val NOTE_TITLE = "Some title"
    val NOTE_CONTENT = "Some content that belongs to your note for demonstration"

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var intCaptor: ArgumentCaptor<Int>

    @Before
    fun setUp() {
        repository = NoteDBRepositoryImpl(dataSource)
    }

    //region createNote() tests
    @Test
    fun `createNote correct parameters passed to dataSource`() = runTest {
        repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        verify(dataSource, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        assertThat(stringCaptor.allValues[1] == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `createNote on success return success response`() = runTest {
        createNoteSuccessResponse()
        val response = repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(response == Response.Success<Note>(null)).isTrue()
    }

    @Test
    fun `createNote on Error return error response`() = runTest {
        createNoteErrorResponse()
        val response = repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(response == Response.Error(INSERT_ERROR)).isTrue()
    }
    //endregion

    //region editNote() tests
    @Test
    fun `editNote correct parameters passed to dataSource`() = runTest {
        repository.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)

        verify(
            dataSource,
            times(1)
        ).editNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )

        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
    }

    @Test
    fun `editNote on success return success response`() = runTest {
        editNoteSuccessResponse()
        val response = repository.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(response == Response.Success<Note>(null)).isTrue()
    }

    @Test
    fun `editNote on error return error response`() = runTest {
        editNoteErrorResponse()
        val response = repository.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(response == Response.Error(UPDATE_ERROR)).isTrue()
    }
    //endregion

    //region getNoteById() functions
    @Test
    fun `getNoteById correct id passed to dataSource`() = runTest {
        repository.getNoteById(id = NOTE_ID)
        verify(dataSource, times(1)).getNoteById(
            capture(intCaptor)
        )
        assertThat(intCaptor.value == NOTE_ID).isTrue()
    }

    @Test
    fun `getNoteById on success return success response`() = runTest {
        getNoteNyIdSuccessResponse()
        val response = repository.getNoteById(id = NOTE_ID)
        assertThat(
            response == Response.Success(
                Note(
                    id = NOTE_ID,
                    title = NOTE_TITLE,
                    content = NOTE_CONTENT
                )
            )
        ).isTrue()
    }

    @Test
    fun `getNoteById on error return error response`() = runTest {
        getNoteNyIdErrorResponse()
        val response = repository.getNoteById(id = NOTE_ID)
        assertThat(
            response == Response.Error(FETCH_ERROR)
        ).isTrue()
    }
    //endregion

    //region helper functions
    private suspend fun createNoteSuccessResponse() {
        `when`(
            dataSource.createNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(Unit)
    }

    private suspend fun createNoteErrorResponse() {
        `when`(
            dataSource.createNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenAnswer {
            throw (Exception(INSERT_ERROR))
        }
    }

    private suspend fun editNoteSuccessResponse() {
        `when`(
            dataSource.editNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(Unit)
    }

    private suspend fun editNoteErrorResponse() {
        `when`(
            dataSource.editNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenAnswer {
            throw (Exception(UPDATE_ERROR))
        }
    }

    private suspend fun getNoteNyIdSuccessResponse() {
        `when`(
            dataSource.getNoteById(
                any(Int::class.java)
            )
        ).thenReturn(
            Note(
                id = NOTE_ID,
                title = NOTE_TITLE,
                content = NOTE_CONTENT
            )
        )
    }

    private suspend fun getNoteNyIdErrorResponse() {
        `when`(
            dataSource.getNoteById(
                any(Int::class.java)
            )
        ).thenAnswer { throw (Exception(FETCH_ERROR)) }
    }
    //endregion
}