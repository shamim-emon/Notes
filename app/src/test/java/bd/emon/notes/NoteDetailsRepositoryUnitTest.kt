package bd.emon.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.INSERT_ERROR
import bd.emon.notes.common.Response
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.data.NoteDBRepositoryImpl
import bd.emon.notes.data.NoteDataSource
import bd.emon.notes.domain.entity.Note
import com.google.common.truth.Truth
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

    val NOTE_TITLE = "Some title"
    val NOTE_CONTENT = "Some content that belongs to your note for demonstration"

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    @Before
    fun setUp() {
        repository = NoteDBRepositoryImpl(dataSource)
    }

    @Test
    fun `createNote correct parameters passed to dataSource`() = runTest {
        repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        verify(dataSource, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        Truth.assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        Truth.assertThat(stringCaptor.allValues[1] == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `createNote on success return success response`() = runTest {
        createNoteSuccessResponse()
        val response = repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        Truth.assertThat(response == Response.Success<Note>(null)).isTrue()
    }

    @Test
    fun `createNote on Error return exception response`() = runTest {
        createNoteErrorResponse()
        val response = repository.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        Truth.assertThat(response == Response.Error(INSERT_ERROR)).isTrue()
    }


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
    //endregion
}