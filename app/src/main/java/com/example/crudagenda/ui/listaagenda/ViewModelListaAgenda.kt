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


    fun deleteAllNotes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun getAllNotesFlow() = repository.getAllNotesFlow()

    suspend fun getAllNotes() {
        safeDbOperation(_getAllNotesResponse) {
            val result = repository.getAllNotes()
            withContext(Dispatchers.Main) {
                _searchNotesResponse.value = ResultData.Success(result)
            }
        }

    }


    suspend fun searchNotes(title: String) {
        safeDbOperation(_getAllNotesResponse) {
            delay(1.seconds)
            val result = repository.searchNotes(title)
            withContext(Dispatchers.Main) {
                _searchNotesResponse.value = ResultData.Success(result)
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }


}