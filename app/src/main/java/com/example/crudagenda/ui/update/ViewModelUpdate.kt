package com.example.crudagenda.ui.update


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelUpdate @Inject constructor(private val contactoRepository: ContactoRepository) :
    ViewModel() {

    suspend fun deleteContacto(contacto: Contacto) {
        viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.deleteContacto(contacto)
        }
    }

    suspend fun updateContacto(contacto: Contacto) {
        viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.updateContact(contacto)
        }
    }


}