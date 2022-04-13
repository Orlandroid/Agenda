package com.example.crudagenda.ui.addcontact



import android.util.Log
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
class ViewModelAddContact @Inject constructor(private val contactoRepository: ContactoRepository) :
    ViewModel() {

    private val TAG = this.javaClass.toString()
    private val _progressBar = MutableLiveData<Boolean>()
    val progresBar: MutableLiveData<Boolean>
        get() = _progressBar


    suspend fun insertContact(name: String, phone: String, birthday: String, note: String, image: String) {
        progresBar.postValue(true)
        Log.w(TAG,"INSERTANDO CONTACTO")
        val contact = Contacto(0, name, phone, birthday, note, image)
        val response = viewModelScope.launch(Dispatchers.IO) {
            contactoRepository.addContacto(contact)
            progresBar.postValue(false)
        }
        response.join()
    }

}