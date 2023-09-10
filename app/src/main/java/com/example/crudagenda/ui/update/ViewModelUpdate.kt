package com.example.crudagenda.ui.update


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.repositorio.NotesRepository
import com.example.crudagenda.ui.base.BaseViewModel
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelUpdate @Inject constructor(private val repository: NotesRepository) :
    BaseViewModel() {

    private val _updateNoteResponse = MutableLiveData<ResultData<Int>>()
    val updateNoteResponse: LiveData<ResultData<Int>>
        get() = _updateNoteResponse

    companion object {
        private const val ERROR_UPDATE = 0
    }

    suspend fun updateNote(
        note: Note
    ) {
        safeDbOperation(_updateNoteResponse) {
            val result = repository.updateNote(note)
            withContext(Dispatchers.Main) {
                if (result == ERROR_UPDATE) {
                    _updateNoteResponse.value = ResultData.Error("Error update")
                } else {
                    _updateNoteResponse.value = ResultData.Success(result)
                }
            }
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }


}