package bd.emon.notes.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNoteUseCase: SearchNoteUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val loadState: LiveData<Boolean>
        get() = _loadState
    private var _loadState: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorState: LiveData<Exception?>
        get() = _errorState
    private var _errorState: MutableLiveData<Exception?> = MutableLiveData()

    val notes: LiveData<List<Note>>
        get() = _notes
    private var _notes: MutableLiveData<List<Note>> = MutableLiveData()

    fun searchNote(keyword: String) {
        _loadState.value = true
        viewModelScope.launch {
            if (keyword.length > 3) {
                try {
                    val response = withContext(dispatcher) {
                        searchNoteUseCase.searchNote(keyword)
                    }
                    _notes.value = response
                    _errorState = MutableLiveData()
                } catch (e: Exception) {
                    _errorState.value = e
                } finally {
                    _loadState.value = false
                }
            } else {
                _loadState.value = false
                _notes.value = emptyList()
            }
        }
    }
}