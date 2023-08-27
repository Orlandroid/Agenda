package com.example.crudagenda.ui.addcontact


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.util.ResultData
import com.example.crudagenda.util.safeDbOperation
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _updateContactResponse = MutableLiveData<ResultData<Long>>()
    val updateContactResponse: MutableLiveData<ResultData<Long>>
        get() = _updateContactResponse


    suspend fun insertNote(
        title: String,
        description: String,
        priority: Priority,
    ) {
        val note = Note(title = title, description = description, priority = priority)
        safeDbOperation(_updateContactResponse) {
            val result = repository.addNote(note)
            _updateContactResponse.value = ResultData.Success(result)
        }
    }
}