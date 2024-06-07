package bd.emon.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.INSERT_ERROR
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.UPDATE_ERROR
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.CreateNoteUseCase
import bd.emon.notes.domain.usecase.EditNoteUseCase
import bd.emon.notes.domain.usecase.GetNoteByIdUseCase
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
    lateinit var getNoteByIdUseCase: GetNoteByIdUseCase

    @Mock
    lateinit var repository: NoteDBRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    val NOTE_ID = 11
    val NOTE_TITLE = "Some title"
    val NOTE_CONTENT = "Some content that belongs to your note for demonstration"

    @Captor
    lateinit var stringCaptor: ArgumentCaptor<String>

    @Captor
    lateinit var intCaptor: ArgumentCaptor<Int>

    @Before
    fun setUp() {
        createNoteUseCase = CreateNoteUseCase(repository)
        editNoteUseCase = EditNoteUseCase(repository)
        getNoteByIdUseCase = GetNoteByIdUseCase(repository)
        viewModel = NoteDetailsViewModel(
            createNoteUseCase = createNoteUseCase,
            editNoteUseCase = editNoteUseCase,
            getNoteByIdUseCase = getNoteByIdUseCase,
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
    fun `createNote on success return Unit`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        createNoteSuccessResponse()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(viewModel.createNote.value == Unit).isTrue()
    }

    @Test
    fun `createNote on success errorState is null`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        createNoteSuccessResponse()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(viewModel.createNote.value == Unit).isTrue()
        assertThat(viewModel.errorState.value == null).isTrue()
    }

    @Test
    fun `createNote before execution loadState is true but false after success`() =
        runTest {
            viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
            assertThat(viewModel.loadState.value == true).isTrue()
            createNoteSuccessResponse()
            testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

            verify(repository, times(1)).createNote(
                capture(stringCaptor),
                capture(stringCaptor)
            )

            assertThat(viewModel.createNote.value == Unit).isTrue()
            assertThat(viewModel.loadState.value == false).isTrue()
        }

    @Test
    fun `createNote before execution loadState is true but false after error `() =
        runTest {
            createNoteErrorResponse()
            viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
            assertThat(viewModel.loadState.value == true).isTrue()
            testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()

            verify(repository, times(1)).createNote(
                capture(stringCaptor),
                capture(stringCaptor)
            )

            assertThat(viewModel.errorState.value!!.localizedMessage == INSERT_ERROR).isTrue()
            assertThat(viewModel.loadState.value == false).isTrue()
        }

    @Test
    fun `createNote on error throw Exception`() = runTest {
        viewModel.createNote(title = NOTE_TITLE, content = NOTE_CONTENT)
        createNoteErrorResponse()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).createNote(
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(viewModel.errorState.value!!.localizedMessage == INSERT_ERROR).isTrue()
    }

    //endregion

    //region EditNote() tests
    @Test
    fun `editNote correct parameters passed to useCase`() = runTest {
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(editNoteUseCase.title == NOTE_TITLE).isTrue()
        assertThat(editNoteUseCase.content == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `editNote correct parameters passed to repository`() = runTest {
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).editNote(
            capture(intCaptor),
            capture(stringCaptor),
            capture(stringCaptor)
        )
        assertThat(intCaptor.value == NOTE_ID).isTrue()
        assertThat(stringCaptor.allValues[0] == NOTE_TITLE).isTrue()
        assertThat(stringCaptor.allValues[1] == NOTE_CONTENT).isTrue()
    }

    @Test
    fun `editNote success return Unit`() = runTest {
        editNoteSuccessResponse()
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Unit).isTrue()
    }

    @Test
    fun `editNote success errorState is null`() = runTest {
        editNoteSuccessResponse()
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Unit).isTrue()
        assertThat(viewModel.errorState!!.value == null).isTrue()
    }

    @Test
    fun `editNote error return error`() = runTest {
        editNoteErrorResponse()
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == UPDATE_ERROR).isTrue()
    }

    @Test
    fun `editNote before execution loadState is true but false after success`() = runTest {
        editNoteSuccessResponse()
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.editNote.value == Unit).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `editNote before execution loadState is true but false after error`() = runTest {
        editNoteErrorResponse()
        viewModel.editNote(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == UPDATE_ERROR).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }
    //endregion

    //region getNoteById()
    @Test
    fun `getNoteById correct id passed to useCase`() = runTest {
        viewModel.getNoteById(id = NOTE_ID)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(getNoteByIdUseCase.id == NOTE_ID).isTrue()
    }

    @Test
    fun `getNoteById correct id passed to repository`() = runTest {
        viewModel.getNoteById(id = NOTE_ID)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).getNoteById(capture(intCaptor))
        assertThat(intCaptor.value == NOTE_ID).isTrue()
    }

    @Test
    fun `getNoteById success return Note`() = runTest {
        getNoteByIdSuccessResponse()
        viewModel.getNoteById(id = NOTE_ID)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(
            viewModel.getNoteById.value == Note(
                id = NOTE_ID,
                title = NOTE_TITLE,
                content = NOTE_CONTENT
            )
        ).isTrue()
    }

    @Test
    fun `getNoteById before execution loadState is true but false  after success`() = runTest {
        getNoteByIdSuccessResponse()
        viewModel.getNoteById(id = NOTE_ID)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(
            viewModel.getNoteById.value == Note(
                id = NOTE_ID,
                title = NOTE_TITLE,
                content = NOTE_CONTENT
            )
        ).isTrue()
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
        ).thenReturn(Unit)
    }

    private suspend fun createNoteErrorResponse() {
        `when`(
            repository.createNote(
                any(String::class.java),
                any(String::class.java)
            )
        ).thenThrow(RuntimeException(INSERT_ERROR))
    }

    private suspend fun editNoteSuccessResponse() {
        `when`(
            repository.editNote(
                any(Int::class.java),
                any(String::class.java),
                any(String::class.java)
            )
        ).thenReturn(Unit)
    }

    private suspend fun editNoteErrorResponse() {
        `when`(
            repository.editNote(
                any(Int::class.java),
                any(String::class.java),
                any(String::class.java)
            )
        ).thenThrow(RuntimeException(UPDATE_ERROR))
    }

    private suspend fun getNoteByIdSuccessResponse() {
        `when`(
            repository.getNoteById(
                any(Int::class.java)
            )
        ).thenReturn(
            Note(id = NOTE_ID, title = NOTE_TITLE, content = NOTE_CONTENT)
        )
    }

    //endregion
}