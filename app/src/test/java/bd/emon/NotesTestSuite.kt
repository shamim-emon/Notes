package bd.emon

import bd.emon.notes.HomeRepositoryUnitTest
import bd.emon.notes.NoteDetailsRepositoryUnitTest
import bd.emon.notes.SearchRepositoryUnitTest
import bd.emon.notes.viewmodel.HomeViewModeUnitTest
import bd.emon.notes.viewmodel.NoteDetailsViewModelUnitTest
import bd.emon.notes.viewmodel.SearchViewModelUnitTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeViewModeUnitTest::class,
    NoteDetailsViewModelUnitTest::class,
    SearchViewModelUnitTest::class,
    HomeRepositoryUnitTest::class,
    NoteDetailsRepositoryUnitTest::class,
    SearchRepositoryUnitTest::class
)
class NotesTestSuite