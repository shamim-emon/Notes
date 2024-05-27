package bd.emon.notes.presentation.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.common.Response
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

    val createNote: LiveData<Response>
        get() = _createNote
    private var _createNote: MutableLiveData<Response> = MutableLiveData()

    val editNote: LiveData<Response>
        get() = _editNote
    private var _editNote: MutableLiveData<Response> = MutableLiveData()

    val getNoteById: LiveData<Response>
        get() = _getNoteById
    private var _getNoteById: MutableLiveData<Response> = MutableLiveData()

    fun createNote(title: String, content: String) {
        _loadState.value = true
        viewModelScope.launch {
            val response = withContext(dispatcher) {
                createNoteUseCase.createNote(title = title, content = content)
            }
            _createNote.value = response
            _loadState.value = false
        }
    }

    fun editNote(title: String, content: String) {
        _loadState.value = true
        viewModelScope.launch {
            val response = withContext(dispatcher) {
                editNoteUseCase.editNote(title = title, content = content)
            }

            _editNote.value = response
            _loadState.value = false
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