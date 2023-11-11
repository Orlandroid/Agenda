package com.example.crudagenda.ui.listaagenda


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.ui.base.BaseViewModel
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ViewModelListaAgenda @Inject constructor(
    private val repository: NotesRepository
) : BaseViewModel() {

    private val _searchNotesResponse = MutableLiveData<ResultData<List<Note>>>()
    val searchNotesResponse: MutableLiveData<ResultData<List<Note>>>
        get() = _searchNotesResponse

    private val _getAllNotesResponse = MutableLiveData<ResultData<List<Note>>>()
    val getAllNotesResponse: MutableLiveData<ResultData<List<Note>>>
        get() = _getAllNotesResponse

    private val _getAllNotesByPriorityResponse = MutableLiveData<ResultData<List<Note>>>()
    val getAllNotesByPriorityResponse = _getAllNotesByPriorityResponse

    private val timeToDelay = 0.4
    //private val timeToDelay = 1


    init {
        viewModelScope.launch {
            //getAllNotesFlow()
        }
    }

    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun getAllNotesFlow() = repository.getAllNotesFlow().flowOn(Dispatchers.IO)

    fun getAllNotes() {
        viewModelScope.launch {
            safeDbOperation(_getAllNotesResponse) {
                delay(timeToDelay.seconds)
                val result = repository.getAllNotes()
                withContext(Dispatchers.Main) {
                    _getAllNotesResponse.value = ResultData.Success(result)
                }
            }
        }
    }

    fun searchNotes(title: String) {
        viewModelScope.launch {
            safeDbOperation(_searchNotesResponse) {
                delay(timeToDelay.seconds)
                val result = repository.searchNotes(title)
                withContext(Dispatchers.Main) {
                    _searchNotesResponse.value = ResultData.Success(result)
                }
            }
        }
    }

    fun getNotesByPriority(priority: String) {
        viewModelScope.launch {
            safeDbOperation(_getAllNotesByPriorityResponse) {
                delay(timeToDelay.seconds)
                val result = repository.getAllNotesByPriority(priority)
                withContext(Dispatchers.Main) {
                    _getAllNotesByPriorityResponse.value = ResultData.Success(result)
                }
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }


}