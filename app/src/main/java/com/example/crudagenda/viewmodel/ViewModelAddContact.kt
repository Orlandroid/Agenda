package com.example.crudagenda.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelAddContact(aplication: Application) : AndroidViewModel(aplication) {

    private val repository = ContactoRepository(aplication)

    fun insertContact(name: String, phone: String, birthday: String, note: String) {
        val contact = Contacto(0, name, phone, birthday, note)
        viewModelScope.launch(Dispatchers.IO) {
            repository.addContacto(contact)
        }
    }


}