package bd.emon.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.INSERT_ERROR
import bd.emon.notes.common.Response
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.UPDATE_ERROR
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.CreateNoteUseCase
import bd.emon.notes.domain.usecase.EditNoteUseCase
import bd.emon.notes.presentation.ui.note.NoteDetailsViewModel
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
class NoteDetailsViewModelUnitTest {

    lateinit var viewModel: NoteDetailsViewModel
    lateinit var createNoteUseCase: CreateNoteUseCase
    lateinit var editNoteUseCase: EditNoteUseCase

    @Mock
    lateinit var repository: NoteDBRepository

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
        createNoteUseCase = CreateNoteUseCase(repository)
        editNoteUseCase = EditNoteUseCase(repository)
        viewModel = NoteDetailsViewModel(
            createNoteUseCase = createNoteUseCase,
            editNoteUseCase = editNoteUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    //region createNote() tests
    @Test
    fun `createNote correct parameters passed to useCase`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(createNoteUseCase.title == NOTE_TITLE).isTrue()
        assertThat(createNoteUseCase.content == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `createNote correct parameters passed to repository`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        assertThat(stringCaptor.allValues[1] == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `createNote on success return success response`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        createNoteSuccessResponse()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(viewModel.createNote.value == Response.Success<Note>(null)).isTrue()
    }

    @Test
    fun `createNote before execution loadState is true but false after success response `() =
        runTest {
            viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
            assertThat(viewModel.loadState.value == true).isTrue()
            createNoteSuccessResponse()
            testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

            verify(repository, times(1)).createNote(
                capture(stringCaptor),
                capture(stringCaptor)
            )

            assertThat(viewModel.createNote.value == Response.Success<Note>(null)).isTrue()
            assertThat(viewModel.loadState.value == false).isTrue()
        }

    @Test
    fun `createNote before execution loadState is true but false after error response `() =
        runTest {
            viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
            assertThat(viewModel.loadState.value == true).isTrue()
            createNoteErrorResponse()
            testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

            verify(repository, times(1)).createNote(
                capture(stringCaptor),
                capture(stringCaptor)
            )

            assertThat(viewModel.createNote.value == Response.Error("Unable to insert Note")).isTrue()
            assertThat(viewModel.loadState.value == false).isTrue()
        }

    @Test
    fun `createNote on eroor return error response`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        createNoteErrorResponse()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(viewModel.createNote.value == Response.Error("Unable to insert Note")).isTrue()
    }

    //endregion

    //region EditNote() tests
    @Test
    fun `editNote correct parameters passed to useCase`() = runTest {
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(editNoteUseCase.title == NOTE_TITLE).isTrue()
        assertThat(editNoteUseCase.content == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `editNote correct parameters passed to repository`() = runTest {
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).editNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        assertThat(stringCaptor.allValues[1] == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `editNote success return success reponse`() = runTest {
        editNoteSuccessResponse()
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Response.Success<Note>(null))
    }
    @Test
    fun `editNote error return error reponse`() = runTest {
        editNoteErrorResponse()
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Response.Error(UPDATE_ERROR)).isTrue()
    }
    @Test
    fun `editNote before execution loadState is true but false after success response`() = runTest {
        editNoteSuccessResponse()
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Response.Success<Note>(null))
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `editNote before execution loadState is true but false after error response`() = runTest {
        editNoteErrorResponse()
        viewModel.editNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Response.Success<Note>(null))
        assertThat(viewModel.loadState.value == false).isTrue()
    }
    //endregion

    //region helper functions

    private suspend fun createNoteSuccessResponse() {
        `when`(
            repository.createNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(
            Response.Success<Note>(null)
        )
    }

    private suspend fun createNoteErrorResponse() {
        `when`(
            repository.createNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(
            Response.Error(INSERT_ERROR)
        )
    }

    private suspend fun editNoteSuccessResponse() {
        `when`(
            repository.editNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(
            Response.Success<Note>(null)
        )
    }

    private suspend fun editNoteErrorResponse() {
        `when`(
            repository.editNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(
            Response.Error(UPDATE_ERROR)
        )
    }
    //endregion
}