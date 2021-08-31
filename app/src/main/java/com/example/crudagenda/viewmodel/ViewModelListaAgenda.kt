package com.example.crudagenda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelListaAgenda(application: Application) : AndroidViewModel(application) {

    private val repository = ContactoRepository(application)

    val contactos = MutableLiveData<List<Contacto>>()

    fun deleteAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllContactos()
        }
    }

    fun getAllContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentContactos = repository.getAllContacs()
            contactos.postValue(currentContactos)
        }
    }


}