package com.example.crudagenda.ui.addcontact


import android.graphics.Bitmap
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
class ViewModelAddContact @Inject constructor(private val repository: ContactoRepository) :
    ViewModel() {

    private val _progressBar = MutableLiveData<Boolean>()
    val progresBar: MutableLiveData<Boolean>
        get() = _progressBar
    private val _isUpateContact = MutableLiveData<Boolean>()
    val isUpateContact: MutableLiveData<Boolean>
        get() = _isUpateContact


    suspend fun insertContact(
        name: String,
        phone: String,
        birthday: String,
        note: String,
        image: Bitmap
    ) {
        progresBar.postValue(true)
        val contact = Contacto(0, name, phone, birthday, note, image)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.addContacto(contact)
                _isUpateContact.postValue(true)
            } catch (e: Exception) {
                _isUpateContact.postValue(false)
            }
            progresBar.postValue(false)
        }
    }

}