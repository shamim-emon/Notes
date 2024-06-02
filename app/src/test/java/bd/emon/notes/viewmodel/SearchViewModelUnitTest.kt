package bd.emon.notes.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import bd.emon.notes.common.TestDispatcherRule
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.usecase.SearchNoteUseCase
import bd.emon.notes.presentation.ui.search.SearchViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelUnitTest {

    lateinit var viewModel: SearchViewModel
    lateinit var searchNoteUseCase: SearchNoteUseCase

    @Inject
    lateinit var repository: NoteDBRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    val keyword = "note123"
//    val noteList = listOf(
//        bd.emon.notes.domain.entity.Note(
//            id = 0,
//            title = "note"
//        )
//    )

    @Before
    fun setUp() {
        searchNoteUseCase = SearchNoteUseCase(repository)
        viewModel = SearchViewModel(
            searchNoteUseCase = searchNoteUseCase,
            dispatcher = testDispatcherRule.testDispatcher
        )
    }

    /**
     * 1.correct keyword passed to useCase
     * 2.correct keyword passed to repository
     * 3.when keyword is less than 3 characters useCase method not called
     * 4.when keyword is less than 3 characters loadState is false
     * 5. success returns note list
     * 6. success- before/after loadState true/false
     * 7. success - errorState null
     * 8. error returns exception
     * 9. error - before/after loadState true/false
     */
}