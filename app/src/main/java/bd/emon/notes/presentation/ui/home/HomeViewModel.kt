package bd.emon.notes.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.DeleteNoteUseCase
import bd.emon.notes.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
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

    val deleteNote: LiveData<Unit>
        get() = _deleteNote
    private var _deleteNote: MutableLiveData<Unit> = MutableLiveData()

    fun getNotes() {
        _loadState.value = true
        viewModelScope.launch {
            try {
                val response = withContext(dispatcher) {
                    getNotesUseCase.getNotes()
                }
                _notes.value = response
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e
            }
            _loadState.value = false
        }
    }

    fun deleteNote(note: Note) {
        _loadState.value = true
        viewModelScope.launch {
            try {
                val response = withContext(dispatcher) {
                    deleteNoteUseCase.deleteNote(note = note)
                }
                _deleteNote.value = response
                _errorState = MutableLiveData()
            } catch (e: Exception) {
                _errorState.value = e
            } finally {
                _loadState.value = false
            }
        }
    }
}