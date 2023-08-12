package com.example.crudagenda.ui.addcontact


import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.repositorio.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddContact @Inject constructor(private val repository: NotesRepository) :
    ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>()
    val progresBar: MutableLiveData<Boolean>
        get() = _progressBar
    private val _isUpateContact = MutableLiveData<Boolean>()
    val isUpateContact: MutableLiveData<Boolean>
        get() = _isUpateContact


    suspend fun insertNote(
        title: String,
        description: String,
        priority: Priority,
    ) {
        val note = Note(title = title, description = description, priority = priority)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addNote(note)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}