package bd.emon.notes

import bd.emon.notes.common.FETCH_ERROR
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
class SearchRepositoryUnitTest {

    lateinit var repository: NoteDBRepository

    @Mock
    lateinit var dataSource: NoteDataSource

    val keyword = "note123"
    val notes = listOf(
        Note(
            id = 0,
            title = "note1",
            content = "content no 1 is note123"
        ),
        Note(
            id = 0,
            title = "note2",
            content = "content no 2  is not note123"
        ),
        Note(
            id = 0,
            title = "note1234",
            content = "This is just another note"
        )
    )
    @Before
    fun setUp() {
        repository = NoteDBRepositoryImpl(dataSource)
    }

    @Test
    fun `searchNote on success return notes`() = runTest {
        searchNotesSuccessWithNotes()
        val response = repository.searchNote(keyword = keyword)
        assertThat(response == notes).isTrue()
    }

    @Test
    fun `searchNote on success return emptyList`() = runTest {
        searchNotesSuccessNoNotes()
        val response = repository.searchNote(keyword = keyword)
        assertThat(response.size).isEqualTo(0)
    }

    @Test
    fun `searchNote on error throw exception`() = runTest {
        try {
            searchNoteError()
            repository.searchNote(keyword = keyword)
        } catch (e: Exception) {
            assertThat(e).hasMessageThat().contains(FETCH_ERROR)
        }
    }

    //region helper functions
    private suspend fun searchNotesSuccessWithNotes() {
        `when`(
            dataSource.searchNote(keyword = keyword)
        ).thenReturn(notes)
    }

    private suspend fun searchNotesSuccessNoNotes() {
        `when`(
            dataSource.searchNote(keyword = keyword)
        ).thenReturn(emptyList())
    }

    private suspend fun searchNoteError() {
        `when`(
            dataSource.searchNote(keyword = keyword)
        ).thenThrow(RuntimeException(FETCH_ERROR))
    }

    //endregion
}