package bd.emon.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.FETCH_ERROR
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.SearchNoteUseCase
import bd.emon.notes.presentation.ui.search.SearchViewModel
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
class SearchViewModelUnitTest {
    @Mock
    lateinit var repository: NoteDBRepository

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    lateinit var viewModel: SearchViewModel
    lateinit var searchNoteUseCase: SearchNoteUseCase

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
        searchNoteUseCase = SearchNoteUseCase(repository)
        viewModel = SearchViewModel(
            searchNoteUseCase = searchNoteUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `searchNote correct keyword passed to useCase`() {
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(searchNoteUseCase.keyword == keyword).isTrue()
    }

    @Test
    fun `searchNote correct keyword passed to repository`() = runTest {
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).searchNote(capture(stringCaptor))
        assertThat(stringCaptor.value == keyword).isTrue()
    }

    @Test
    fun `searchNote when keyword is less than 3 characters keyword is not passed to useCase`() =
        runTest {
            viewModel.searchNote(keyword = "ab")
            testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
            assertThat(searchNoteUseCase.isKeywordInitialised).isFalse()
        }

    @Test
    fun `searchNote when keyword is less than 3 characters loadState is false `() = runTest {
        viewModel.searchNote(keyword = "ab")
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `searchNote when keyword is less than 3 characters noteList is empty`() = runTest {
        viewModel.searchNote(keyword = "ab")
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value!!.size).isEqualTo(0)
    }

    @Test
    fun `searchNote success returns notes`() = runTest {
        searchNoteSuccessWithNoteList()
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value == notes).isTrue()
    }

    @Test
    fun `searchNote success but no keyword match returns emptyList`() = runTest {
        searchNoteSuccessNoMatchEmptyList()
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value!!.size).isEqualTo(0)
    }

    @Test
    fun `searchNote success errorState null`() = runTest {
        searchNoteSuccessWithNoteList()
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value == null).isTrue()
    }

    @Test
    fun `searchNote error return exception`() = runTest {
        searchNoteErrorThrowException()
        viewModel.searchNote(keyword = keyword)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == FETCH_ERROR).isTrue()
    }

    @Test
    fun `searchNote loadState true before execution but false after success`() = runTest {
        searchNoteSuccessWithNoteList()
        viewModel.searchNote(keyword = keyword)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value == notes).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `searchNote loadState true before execution but false after exception`() = runTest {
        searchNoteErrorThrowException()
        viewModel.searchNote(keyword = keyword)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == FETCH_ERROR).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    //region helper functions
    private fun searchNoteSuccessWithNoteList() = runTest {
        `when`(
            repository.searchNote(
                any(String::class.java)
            )
        ).thenReturn(notes)
    }

    private fun searchNoteSuccessNoMatchEmptyList() = runTest {
        `when`(
            repository.searchNote(
                any(String::class.java)
            )
        ).thenReturn(emptyList())
    }

    private fun searchNoteErrorThrowException() = runTest {
        `when`(
            repository.searchNote(
                any(String::class.java)
            )
        ).thenThrow(RuntimeException(FETCH_ERROR))
    }
    //endregion
}