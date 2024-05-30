package bd.emon.notes.presentation.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.CreateNoteUseCase
import bd.emon.notes.domain.usecase.EditNoteUseCase
import bd.emon.notes.domain.usecase.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val editNoteUseCase: EditNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val loadState: LiveData<Boolean>
        get() = _loadState
    private var _loadState: MutableLiveData<Boolean> = MutableLiveData(false)

    val errorState: LiveData<Exception>
        get() = _errorState
    private var _errorState: MutableLiveData<Exception> = MutableLiveData(null)

    val createNote: LiveData<Unit>
        get() = _createNote
    private var _createNote: MutableLiveData<Unit> = MutableLiveData()

    val editNote: LiveData<Unit>
        get() = _editNote
    private var _editNote: MutableLiveData<Unit> = MutableLiveData()

    val getNoteById: LiveData<Note>
        get() = _getNoteById
    private var _getNoteById: MutableLiveData<Note> = MutableLiveData()

    fun createNote(title: String, content: String) {
        _loadState.value = true
        viewModelScope.launch {
            try {
                val response = withContext(dispatcher) {
                    createNoteUseCase.createNote(title = title, content = content)
                }
                _createNote.value = response
                _errorState = MutableLiveData(null)
            } catch (e: Exception) {
                _errorState.value = e
            } finally {
                _loadState.value = false
            }
        }
    }

    fun editNote(title: String, content: String) {
        _loadState.value = true
        viewModelScope.launch {
            try {
                val response = withContext(dispatcher) {
                    editNoteUseCase.editNote(title = title, content = content)
                }
                _editNote.value = response
                _errorState = MutableLiveData(null)
            } catch (e: Exception) {
                _errorState.value = e
            } finally {
                _loadState.value = false
            }
        }
    }

    fun getNoteById(id: Int) {
        _loadState.value = true
        viewModelScope.launch {
            val response = withContext(dispatcher) {
                getNoteByIdUseCase.getNoteById(id)
            }
            _getNoteById.value = response
            _loadState.value = false
        }
    }
}