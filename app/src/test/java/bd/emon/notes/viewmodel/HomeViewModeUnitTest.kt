package bd.emon.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.DELETE_ERROR
import bd.emon.notes.common.FETCH_ERROR
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.common.any
import bd.emon.notes.common.capture
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.DeleteNoteUseCase
import bd.emon.notes.domain.usecase.GetNotesUseCase
import bd.emon.notes.presentation.ui.home.HomeViewModel
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
class HomeViewModeUnitTest {

    lateinit var viewModel: HomeViewModel
    lateinit var getNotesUseCase: GetNotesUseCase
    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Captor
    lateinit var noteCaptor: ArgumentCaptor<Note>

    @Mock
    lateinit var repository: NoteDBRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

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
        getNotesUseCase = GetNotesUseCase(repository)
        deleteNoteUseCase = DeleteNoteUseCase(repository)
        viewModel = HomeViewModel(
            getNotesUseCase = getNotesUseCase,
            deleteNoteUseCase = deleteNoteUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    //region getNotes() tests
    @Test
    fun `getNotes on success return  notes`() = runTest {
        getNotesSuccess()
        viewModel.getNotes()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value == notes).isTrue()
    }

    @Test
    fun `getNotes on success return  no note`() = runTest {
        getNotesSuccessNoNotes()
        viewModel.getNotes()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value == emptyList<Note>()).isTrue()
    }

    @Test
    fun `getNotes success before execution loadState true but false on completion`() = runTest {
        getNotesSuccess()
        viewModel.getNotes()
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.notes.value == notes).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `getNotes on error return exception`() = runTest {
        getNotesError()
        viewModel.getNotes()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == FETCH_ERROR).isTrue()
    }

    @Test
    fun `getNotes error before execution loadState true but false on completion`() = runTest {
        getNotesError()
        viewModel.getNotes()
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == FETCH_ERROR).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    //endregion
    //region deleteNote()
    @Test
    fun `deleteNote correct note passed to useCase`() {
        viewModel.deleteNote(note = note)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(deleteNoteUseCase.note == note).isTrue()
    }

    @Test
    fun `deleteNote correct note passed to repository`() = runTest {
        viewModel.deleteNote(note = note)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        verify(repository, times(1)).deleteNote(capture(noteCaptor))
        assertThat(noteCaptor.allValues[0] == note).isTrue()
    }

    @Test
    fun `deleteNote succeed return unit`() = runTest {
        deleteNoteSuccess()
        viewModel.deleteNote(note = note)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.deleteNote.value == Unit).isTrue()
    }

    @Test
    fun `deleteNote before execution loadState true but false after success`() = runTest {
        deleteNoteSuccess()
        viewModel.deleteNote(note = note)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.deleteNote.value == Unit).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    @Test
    fun `deleteNote success errorState null`() = runTest {
        deleteNoteSuccess()
        viewModel.deleteNote(note = note)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.deleteNote.value == Unit).isTrue()
        assertThat(viewModel.errorState.value == null).isTrue()
    }

    @Test
    fun `deleteNote failed return error`() = runTest {
        deleteNoteError()
        viewModel.deleteNote(note = note)
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == DELETE_ERROR).isTrue()
    }

    @Test
    fun `deleteNote before execution loadState true but false after error`() = runTest {
        deleteNoteError()
        viewModel.deleteNote(note = note)
        assertThat(viewModel.loadState.value == true).isTrue()
        testDispatcherRule.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.errorState.value!!.localizedMessage == DELETE_ERROR).isTrue()
        assertThat(viewModel.loadState.value == false).isTrue()
    }

    //endregion
    //region helper functions
    private suspend fun getNotesSuccess() {
        `when`(
            repository.getNotes()
        ).thenReturn(notes)
    }

    private suspend fun getNotesSuccessNoNotes() {
        `when`(
            repository.getNotes()
        ).thenReturn(emptyList())
    }

    private suspend fun getNotesError() {
        `when`(
            repository.getNotes()
        ).thenThrow(RuntimeException(FETCH_ERROR))
    }

    private suspend fun deleteNoteSuccess() {
        `when`(
            repository
                .deleteNote(any(Note::class.java))
        ).thenReturn(Unit)
    }

    private suspend fun deleteNoteError() {
        `when`(
            repository
                .deleteNote(any(Note::class.java))
        ).thenThrow(RuntimeException(DELETE_ERROR))
    }
    //endregion
}