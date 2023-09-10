package com.example.crudagenda.ui.addcontact


import androidx.lifecycle.MutableLiveData
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.ui.base.BaseViewModel
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelAddContact @Inject constructor(
    private val repository: NotesRepository
) :
    BaseViewModel() {

    private val _updateContactResponse = MutableLiveData<ResultData<Long>>()
    val updateContactResponse: MutableLiveData<ResultData<Long>>
        get() = _updateContactResponse


    suspend fun insertNote(
        note: Note,
    ) {
        safeDbOperation(_updateContactResponse) {
            val result = repository.addNote(note)
            withContext(Dispatchers.Main) {
                _updateContactResponse.value = ResultData.Success(result)
            }
        }
    }

}