package com.example.crudagenda.ui.addcontact


import androidx.lifecycle.MutableLiveData
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.modelo.Priority
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.ui.base.BaseViewModel
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelAddContact @Inject constructor(
    private val repository: NotesRepository
) :
    BaseViewModel() {

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
        note: Note,
    ) {
        safeDbOperation(_updateContactResponse) {
            val result = repository.addNote(note)
            _updateContactResponse.value = ResultData.Success(result)
        }
    }

}