package bd.emon.notes.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bd.emon.notes.common.Response
import bd.emon.notes.domain.entity.Note
import bd.emon.notes.domain.usecase.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {

    val loadState: LiveData<Boolean>
        get() = _loadState
    private var _loadState: MutableLiveData<Boolean> = MutableLiveData(false)

    val notes: LiveData<Response>
        get() = _notes
    private var _notes: MutableLiveData<Response> = MutableLiveData()

    fun getNotes() {
        _loadState.value = true
        viewModelScope.launch {
            val response = withContext(dispatcher){
                getNotesUseCase.getNotes()
            }
            _notes.value = response
            _loadState.value = false

        }
    }

}