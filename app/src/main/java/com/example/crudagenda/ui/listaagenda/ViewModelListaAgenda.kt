package com.example.crudagenda.ui.listaagenda


import androidx.lifecycle.*
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelListaAgenda @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {


    fun deleteAllContacts() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    fun getAllContacts(): LiveData<List<Note>> = repository.getAllNotes()


}