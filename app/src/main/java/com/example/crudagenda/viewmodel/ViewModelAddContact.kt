package com.example.crudagenda.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddContact @Inject constructor(private val contactoRepository: ContactoRepository) :
    ViewModel() {

    fun insertContact(name: String, phone: String, birthday: String, note: String) {
        val contact = Contacto(0, name, phone, birthday, note)
        viewModelScope.launch(Dispatchers.IO) {
                contactoRepository.addContacto(contact)
        }
    }

}