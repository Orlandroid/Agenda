package com.example.crudagenda.ui.update


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelUpdate @Inject constructor(private val contactoRepository: NotesRepository) :
    ViewModel() {

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        contactoRepository.deleteNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        contactoRepository.updateNote(note)
    }


}