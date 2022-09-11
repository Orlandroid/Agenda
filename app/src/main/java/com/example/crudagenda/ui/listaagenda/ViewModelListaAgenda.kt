package com.example.crudagenda.ui.listaagenda


import androidx.lifecycle.*
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelListaAgenda @Inject constructor(
    private val repository: ContactoRepository
) :
    ViewModel() {


    suspend fun deleteAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllContactos()
        }
    }

    fun getAllContacts(): LiveData<List<Contacto>> {
        return repository.getAllContacs()
    }


}