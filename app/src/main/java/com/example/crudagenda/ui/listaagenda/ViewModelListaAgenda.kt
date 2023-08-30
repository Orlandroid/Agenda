package com.example.crudagenda.ui.listaagenda


import androidx.lifecycle.*
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.ui.base.BaseViewModel
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    private val wait = 100L

    fun deleteAllContacts() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun getAllNotesFlow() = repository.getAllNotesFlow()

    suspend fun getAllNotes() {
        safeDbOperation(_getAllNotesResponse) {
            val result = repository.getAllNotes()
            _searchNotesResponse.value = ResultData.Success(result)
        }

    }


    suspend fun searchNotes(title: String) {
        safeDbOperation(_getAllNotesResponse) {
            delay(wait)
            val result = repository.searchNotes(title)
            _searchNotesResponse.value = ResultData.Success(result)
        }
    }


}