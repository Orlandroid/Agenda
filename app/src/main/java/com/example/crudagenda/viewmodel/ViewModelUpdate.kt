package com.example.crudagenda.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelUpdate(application: Application) : AndroidViewModel(application) {

    private val repository = ContactoRepository(application)

    fun deleteContacto(contacto: Contacto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContacto(contacto)
        }
    }

    fun updateContacto(contacto: Contacto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateContact(contacto)
        }
    }


}