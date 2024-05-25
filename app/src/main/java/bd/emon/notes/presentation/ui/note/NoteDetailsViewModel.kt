package bd.emon.notes.presentation.ui.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.common.Response
import bd.emon.notes.domain.usecase.CreateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val createNoteUseCase: CreateNoteUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val loadState: LiveData<Boolean>
        get() = _loadState

    private var _loadState: MutableLiveData<Boolean> = MutableLiveData(false)

    val createNote: LiveData<Response>
        get() = _createNote
    private var _createNote: MutableLiveData<Response> = MutableLiveData()
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
}