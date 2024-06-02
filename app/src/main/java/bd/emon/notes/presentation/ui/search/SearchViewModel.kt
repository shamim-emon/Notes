package bd.emon.notes.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.domain.usecase.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNoteUseCase: SearchNoteUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    fun searchNote(keyword: String) {
        viewModelScope.launch {
            searchNoteUseCase.searchNote(keyword)
        }
    }
}