package com.example.crudagenda.ui.listaagenda


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelListaAgenda @Inject constructor(private val contactoRepository: ContactoRepository) :
    ViewModel() {


    val contactos = MutableLiveData<List<Contacto>>()

    fun deleteAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.deleteAllContactos()
        }
    }

    fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentContactos = contactoRepository.getAllContacs()
            contactos.postValue(currentContactos)
        }
    }


}