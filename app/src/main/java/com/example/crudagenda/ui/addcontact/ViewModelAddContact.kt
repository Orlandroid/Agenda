package com.example.crudagenda.ui.addcontact


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.repositorio.ContactoRepository
import com.example.crudagenda.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelAddContact @Inject constructor(private val contactoRepository: ContactoRepository) :
    ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>()
    val progresBar: MutableLiveData<Boolean>
        get() = _progressBar


    fun insertContact(name: String, phone: String, birthday: String, note: String, image: String) {
        progresBar.postValue(true)
        val contact = Contacto(0, name, phone, birthday, note, image)
        viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.addContacto(contact)
            progresBar.postValue(false)
        }
    }

}